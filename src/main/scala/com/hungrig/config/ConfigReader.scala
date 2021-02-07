package com.hungrig.config
import com.hungrig.models.StreamConfig
import pureconfig.ConfigSource
import pureconfig._
import pureconfig.generic.auto._
object ConfigReader {

  val streamConfig = ConfigSource.default.loadOrThrow[StreamConfig]
}
