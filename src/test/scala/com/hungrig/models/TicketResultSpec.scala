package com.hungrig.models

import java.time.ZonedDateTime

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.circe.literal._
import io.circe.syntax.EncoderOps

class TicketResultSpec extends AnyWordSpec with Matchers {

  "TicketResponse" should {
    val ticketResultJson =
      json"""{
          "id": 1,
          "created_at": "2021-01-04T15:11:04Z",
          "updated_at": "2021-01-04T17:03:16Z",
          "domain": "Domain"
        }"""
    val result = TicketResult(
      1L,
      Some(ZonedDateTime.parse("2021-01-04T15:11:04Z")),
      Some(ZonedDateTime.parse("2021-01-04T17:03:16Z")),
      Some("Domain")
    )

    "encoder" in {
      result.asJson shouldBe ticketResultJson
    }

    "decoder" in {
      TicketResult.decoder.decodeJson(ticketResultJson) shouldBe Right(result)
    }

  }
}
