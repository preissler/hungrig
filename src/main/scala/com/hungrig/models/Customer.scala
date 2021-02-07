package com.hungrig.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class Customer(id: String, time: SearchTime)
case class CustomerCredentials(id: String, time: SearchTime, token: String, url: String)

object Customer {
  implicit val decoder: Decoder[Customer] = deriveDecoder[Customer]
}
