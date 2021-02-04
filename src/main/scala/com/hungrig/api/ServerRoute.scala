package com.hungrig.api

import akka.http.scaladsl.server.Directives.pathPrefix
import com.typesafe.scalalogging.StrictLogging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{pathPrefix, _}

class ServerRoute extends StrictLogging {

  val apiRoute = {
    (pathPrefix("api" / "hungrig") & get) {
      path("health-check") {
        complete(StatusCodes.OK)
      }
    }
  }
}
