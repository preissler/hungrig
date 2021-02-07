package com.hungrig.models

case class Throttle(elements: Int, per: Int)
case class StreamConfig(throttle: Throttle)
