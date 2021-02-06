package com.hungrig.models

import cats.implicits._
import io.circe.Decoder
import io.circe.generic.semiauto._

sealed trait Contact
final case class EmailAddress(name: Option[String], address: Option[String]) extends Contact
final case class Voice(formatted_phone: Option[String], phone: String, name: Option[String], brand_id: Option[Long])
    extends Contact
final case class Source(from: Option[Contact], to: Option[Contact])
final case class Via(channel: Option[String], source: Option[Source], rel: Option[String])

object EmailAddress {
  implicit val decoder: Decoder[EmailAddress] = deriveDecoder[EmailAddress]
}
object Voice {
  implicit val decoder: Decoder[Voice] = deriveDecoder[Voice]
}
object Source {
  implicit val decoder: Decoder[Source] = deriveDecoder[Source]
}
object Via {
  implicit val decoder: Decoder[Via] = deriveDecoder[Via]
}
object Contact {
  implicit val decoder: Decoder[Contact] =
    List[Decoder[Contact]](Decoder[Voice].widen, Decoder[EmailAddress].widen).reduceLeft(_.or(_))
}
