package com.jbb.weather.model

import io.circe._

enum ConditionType:
  case Snow
  case Thunderstorm
  case Drizzle
  case Rain
  case Clouds
  case Clear
  case Atmosphere

object ConditionType:
  implicit val encoder: Encoder[ConditionType] = Encoder[String].contramap(_.toString)
  // Not handling error scenario (This should probably be handled)
  implicit val decoder: Decoder[ConditionType] = Decoder[String].map(ConditionType.valueOf)