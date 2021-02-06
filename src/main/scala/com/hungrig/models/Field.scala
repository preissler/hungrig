package com.hungrig.models
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

final case class Field(id: Long, value: Option[String])
object Field {
  implicit val decoder: Decoder[Field] = deriveDecoder[Field]
}
