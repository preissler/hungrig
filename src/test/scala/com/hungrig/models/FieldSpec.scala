package com.hungrig.models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.circe.literal._

class FieldSpec extends AnyWordSpec with Matchers {
  "Field" should {
    "decode" in {
      val jsonField = json"""
            {
              "id": 360015007599,
              "value": "5"
            }"""
      val field = Field(360015007599L, Some("5"))
      Field.decoder.decodeJson(jsonField) shouldBe Right(field)

    }
  }
}
