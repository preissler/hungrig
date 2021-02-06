package com.hungrig.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class SatisfactionRating(
  id: Option[Long],
  score: Option[String],
  comment: Option[String],
  reason: Option[String],
  reason_id: Option[Long]
)

object SatisfactionRating {
  implicit val decoder: Decoder[SatisfactionRating] = deriveDecoder[SatisfactionRating]
}
