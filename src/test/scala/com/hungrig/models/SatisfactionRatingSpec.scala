package com.hungrig.models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.circe.literal._

class SatisfactionRatingSpec extends AnyWordSpec with Matchers {

  "SatisfactionRating" should {
    "decode" in {
      val jsonSatisfactionRating =
        json"""{
            "score": "good",
            "id": 361768652559,
            "comment": null,
            "reason": "No reason provided",
            "reason_id": 360001002260
          }"""

      val satisfactionRating =
        SatisfactionRating(Some(361768652559L), Some("good"), None, Some("No reason provided"), Some(360001002260L))

      SatisfactionRating.decoder.decodeJson(jsonSatisfactionRating) shouldBe Right(satisfactionRating)

    }
  }

}
