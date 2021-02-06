package com.hungrig.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class SearchTime(time: Long)

object SearchTime {
  implicit val decoder: Decoder[SearchTime] = deriveDecoder[SearchTime]
  implicit val encoder: Encoder[SearchTime] = deriveEncoder[SearchTime]
}
