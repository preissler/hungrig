package com.hungrig

import akka.http.javadsl.model.headers.HttpCredentials
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.stream.scaladsl.{Balance, Broadcast, Flow, GraphDSL, MergePreferred, Partition, Source}
import com.hungrig.models.{CustomerCredentials, SearchTime, TicketsResponse}
import com.typesafe.scalalogging.StrictLogging
import AkkaCluster._
import akka.NotUsed
import akka.http.scaladsl.Http
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ClosedShape, FanOutShape, FlowShape, Outlet}
import com.hungrig.config.ConfigReader
import io.circe.Error
import io.circe.parser.decode
import io.circe.syntax._

import scala.concurrent.Future
import scala.concurrent.duration._

class StreamProcess(customer: CustomerCredentials) extends StrictLogging {
  logger.info(s"Creating Stream for customer ID ${customer.id}")
  import system.dispatcher
  val request: Long => HttpRequest = buildRequest(customer.url, customer.token)
  val throttle = ConfigReader.streamConfig.throttle

  def buildRequest(url: String, token: String)(time: Long): HttpRequest = {
    logger.info(s"Building HttpRequest for $time ")
    lazy val authorization = HttpCredentials.createOAuth2BearerToken(token)
    lazy val completeUrl = url + time
    Get(Uri(completeUrl)).addCredentials(authorization)
  }

  val collectResponseOrFailFlow: Flow[Future[Either[Error, TicketsResponse]], Future[TicketsResponse], NotUsed] =
    Flow.fromFunction((r: Future[Either[Error, TicketsResponse]]) => {
      r.map { e =>
        val res = e match {
          case Left(e) => throw new RuntimeException("Error in stream")
          case Right(r) => r
        }
        res
      }
    })

  val decodeTicketResponse: Flow[
    (Future[HttpResponse], Future[SearchTime]),
    (Future[Either[Error, TicketsResponse]], Future[SearchTime]),
    NotUsed
  ] =
    Flow.fromFunction(
      (query: (Future[HttpResponse], Future[SearchTime])) =>
        (
          query._1.flatMap(
            response =>
              Unmarshal(response.entity)
                .to[String]
                .map(decode[TicketsResponse](_))
          ),
          query._2
      )
    )

  def runStream(): Unit = {
    logger.info(s"Starting Stream for customer ID ${customer.id}")

    val source = Source.single(Future { customer.time })

    def ticketReaderGraph() = GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._
      val sourceShape = builder.add(source)
      val mergeShape = builder.add(MergePreferred[Future[SearchTime]](1))

      val connectionFlowShape = builder
        .add(
          Flow
            .fromFunction(
              (ft: Future[SearchTime]) =>
                ({
                  ft.map { f =>
                    Http().singleRequest(request(f.time))
                  }.flatten
                }, ft)
            )
            .throttle(throttle.elements, throttle.per seconds)
        )

      val decodeTicketResponseShape = builder.add(decodeTicketResponse)

      sourceShape ~> mergeShape ~> connectionFlowShape ~> decodeTicketResponseShape

      ClosedShape
    }
  }

}
