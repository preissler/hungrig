package com.hungrig.models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.circe.literal._

class ViaSpec extends AnyWordSpec with Matchers {

  "Via" should {
    "decode email" in {
      val jsonVia =
        json"""{
                "channel": "email",
                "source": {
                  "from": {
                    "address": "artur@xxx.com",
                    "name": "Artur Perun"
                  },
                  "to": {
                    "name": "xxx",
                    "address": "support@d3v-xxx.zendesk.com"
                  },
                  "rel": null
                }
            }"""

      val sourceResult = Source(
        Some(EmailAddress(Some("Artur Perun"), Some("artur@xxx.com"))),
        Some(EmailAddress(Some("xxx"), Some("support@d3v-xxx.zendesk.com")))
      )
      val viaResult = Via(Some("email"), Some(sourceResult), None)
      Via.decoder.decodeJson(jsonVia) shouldBe Right(viaResult)

    }
    "decode Voice" in {
      val jsonVia =
        json"""{
                "channel": "voice",
                "source": {
                  "rel": "voicemail",
                  "from": {
                    "formatted_phone": "+1 (602) 220-2269",
                    "phone": "+16022202269",
                    "name": "Caller +1 (602) 220-2269"
                  },
                  "to": {
                    "formatted_phone": "+1 (619) 639-9312",
                    "phone": "+16196399312",
                    "name": "xxx",
                    "brand_id": 360001393519
                  }
                }
            }"""

      val sourceResult = Source(
        Some(Voice(Some("+1 (602) 220-2269"), "+16022202269", Some("Caller +1 (602) 220-2269"), None)),
        Some(Voice(Some("+1 (619) 639-9312"), "+16196399312", Some("xxx"), Some(360001393519L)))
      )
      val viaResult = Via(Some("voice"), Some(sourceResult), None)
      Via.decoder.decodeJson(jsonVia) shouldBe Right(viaResult)
    }

  }

}
