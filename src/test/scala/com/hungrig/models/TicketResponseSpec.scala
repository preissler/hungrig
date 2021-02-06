package com.hungrig.models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.circe.literal._
class TicketResponseSpec extends AnyWordSpec with Matchers {
  "TicketResponse" should {
    "decode" in {

      val ticketsResponseJson = json"""{
                "tickets": [],
                "count": 6,
                "end_of_stream": true,
                "next_page": "https://xxx.com/api/v2/incremental/tickets.json?start_time=1611655571",
                "end_time": 1611655571
               }"""

      val ticketsResponse = TicketsResponse(
        List(),
        6,
        true,
        Some("https://xxx.com/api/v2/incremental/tickets.json?start_time=1611655571"),
        1611655571L
      )
      TicketsResponse.decoder.decodeJson(ticketsResponseJson) shouldBe Right(ticketsResponse)
    }

  }
}
