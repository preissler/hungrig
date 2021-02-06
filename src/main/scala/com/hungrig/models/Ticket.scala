package com.hungrig.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

import java.time.ZonedDateTime

case class Ticket(
  id: Long,
  url: Option[String],
  external_id: Option[Long],
  via: Option[Via],
  created_at: Option[ZonedDateTime],
  updated_at: Option[ZonedDateTime],
  `type`: Option[String],
  subject: Option[String],
  raw_subject: Option[String],
  description: Option[String],
  priority: Option[String],
  status: String,
  recipient: Option[String],
  requester_id: Option[Long],
  submitter_id: Option[Long],
  assignee_id: Option[Long],
  organization_id: Option[Long],
  group_id: Option[Long],
  collaborator_ids: List[Long],
  follower_ids: List[Long],
  email_cc_ids: List[Long],
  forum_topic_id: Option[Long],
  problem_id: Option[Long],
  has_incidents: Option[Boolean],
  is_public: Option[Boolean],
  due_at: Option[ZonedDateTime],
  tags: List[String],
  custom_fields: List[Field],
  satisfaction_rating: Option[SatisfactionRating],
  sharing_agreement_ids: List[Long],
  fields: List[Field],
  followup_ids: List[Long],
  ticket_form_id: Option[Long],
  brand_id: Option[Long],
  satisfaction_probability: Option[String],
  allow_channelback: Option[Boolean],
  allow_attachments: Option[Boolean],
  generated_timestamp: Option[Long]
)
object Ticket {
  implicit val decoder: Decoder[Ticket] = deriveDecoder[Ticket]
}
