package com.hungrig.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

final case class TicketsResponse(
  tickets: List[Ticket],
  count: Int,
  end_of_stream: Boolean,
  next_page: Option[String],
  end_time: Long
)

object TicketsResponse {
  implicit val decoder: Decoder[TicketsResponse] = deriveDecoder[TicketsResponse]
}
