package com.hungrig

import akka.http.scaladsl.Http
import com.typesafe.scalalogging.StrictLogging
import com.hungrig.AkkaCluster._
import com.hungrig.api.ServerRoute

object Main extends App with StrictLogging {
  logger.info("Starting up")

  val server = new ServerRoute

  Http().bindAndHandle(server.apiRoute, "0.0.0.0", 8081)

}
