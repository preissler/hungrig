package com.hungrig

import akka.actor.ActorSystem

object AkkaCluster {
  implicit val system = ActorSystem("HungrigAkkaSystem")
}
