package com.jbb.weather.model

import io.circe._

enum TempType:
  case COLD
  case MODERATE
  case WARM

object TempType:
  implicit val tempTypeEncoder: Encoder[TempType] = Encoder[String].contramap(_.toString)
  // Not handling error scenario (This should probably be handled)
  implicit val tempTypeDecoder: Decoder[TempType] = Decoder[String].map(TempType.valueOf)
