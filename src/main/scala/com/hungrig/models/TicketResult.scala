package com.hungrig.models

import java.time.ZonedDateTime

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

final case class TicketResult(
  id: Long,
  created_at: Option[ZonedDateTime],
  updated_at: Option[ZonedDateTime],
  domain: Option[String]
)
object TicketResult {
  implicit val decoder: Decoder[TicketResult] = deriveDecoder[TicketResult]
  implicit val encoder: Encoder[TicketResult] = deriveEncoder[TicketResult]
}
