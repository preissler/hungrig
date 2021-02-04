package com.hungrig.api

import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HealthCheckSpec extends AnyWordSpec with Matchers with ScalatestRouteTest {
  val serverRoute = new ServerRoute

  "HealthCheck" should {
    "Return Ok " in {
      Get("/api/hungrig/health-check") ~> serverRoute.apiRoute ~> check {
        status shouldBe StatusCodes.OK

      }
    }
  }

}
