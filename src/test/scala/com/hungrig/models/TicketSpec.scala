package com.hungrig.models

import java.time.ZonedDateTime

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.circe.literal._
class TicketSpec extends AnyWordSpec with Matchers {
  "Via" should {
    "decode" in {
      val jsonTicket =
        json"""{
              "url": "https://xxx.com/api/v2/tickets/4481.json",
              "id": 4481,
              "external_id": null,
              "via": {
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
                    "name": "Kaizo",
                    "brand_id": 360001393519
                  }
                }
              },
              "created_at": "2021-01-07T16:36:51Z",
              "updated_at": "2021-01-07T16:36:51Z",
              "type": null,
              "subject": "Voicemail from: Caller +1 (602) 220-2269",
              "raw_subject": "Voicemail from: Caller +1 (602) 220-2269",
              "description": "Call from: +1 (602) 220-2269\\nTime of call: January 7, 2021 at 4:36:11 PM",
              "priority": null,
              "status": "new",
              "recipient": null,
              "requester_id": 377604255940,
              "submitter_id": 377604255940,
              "assignee_id": null,
              "organization_id": null,
              "group_id": 360003262519,
              "collaborator_ids": [],
              "follower_ids": [],
              "email_cc_ids": [],
              "forum_topic_id": null,
              "problem_id": null,
              "has_incidents": false,
              "is_public": false,
              "due_at": null,
              "tags": [],
              "custom_fields": [
                {
                  "id": 360015007579,
                  "value": "Some"
                },
                {
                  "id": 360015007599,
                  "value": null
                }
              ],
              "satisfaction_rating": {
                "score": "unoffered"
              },
              "sharing_agreement_ids": [],
              "fields": [
                {
                  "id": 360015007579,
                  "value": null
                }
              ],
              "followup_ids": [],
              "ticket_form_id": 360000763199,
              "brand_id": 360001393519,
              "satisfaction_probability": null,
              "allow_channelback": false,
              "allow_attachments": true,
              "generated_timestamp": 1610037411
            }"""

      val sourceResult = Source(
        Some(Voice(Some("+1 (602) 220-2269"), "+16022202269", Some("Caller +1 (602) 220-2269"), None)),
        Some(Voice(Some("+1 (619) 639-9312"), "+16196399312", Some("Kaizo"), Some(360001393519L)))
      )
      val viaResult = Via(Some("voice"), Some(sourceResult), None)
      val fields = List(Field(360015007579L, None))
      val custom_fields = List(Field(360015007579L, Some("Some")), Field(360015007599L, None))
      val satisfactionRating = SatisfactionRating(None, Some("unoffered"), None, None, None)
      val ticket = Ticket(
        4481L,
        Some("https://xxx.com/api/v2/tickets/4481.json"),
        None,
        Some(viaResult),
        Some(ZonedDateTime.parse("2021-01-07T16:36:51Z")),
        Some(ZonedDateTime.parse("2021-01-07T16:36:51Z")),
        None,
        Some("Voicemail from: Caller +1 (602) 220-2269"),
        Some("Voicemail from: Caller +1 (602) 220-2269"),
        Some("Call from: +1 (602) 220-2269\\nTime of call: January 7, 2021 at 4:36:11 PM"),
        None,
        "new",
        None,
        Some(377604255940L),
        Some(377604255940L),
        None,
        None,
        Some(360003262519L),
        List(),
        List(),
        List(),
        None,
        None,
        Some(false),
        Some(false),
        None,
        List(),
        custom_fields,
        Some(satisfactionRating),
        List(),
        fields,
        List(),
        Some(360000763199L),
        Some(360001393519L),
        None,
        Some(false),
        Some(true),
        Some(1610037411L)
      )

      Ticket.decoder.decodeJson(jsonTicket) shouldBe Right(ticket)

    }
  }
}
