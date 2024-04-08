package com.jbb.weather.client.model

import cats.effect.Concurrent
import io.circe.{Decoder, Encoder}
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}


case class WeatherClientResponse(
                                  current: WeatherClientResponseCurrent,
                                  alerts: Option[Seq[WeatherClientResponseAlert]]
                                )

case class WeatherClientResponseCurrent(
                                         temp: Double,
                                         weather: Seq[WeatherClientResponseCurrentWeather],
                                       )

case class WeatherClientResponseCurrentWeather(
                                                main: String,
                                              )

case class WeatherClientResponseAlert(
                                       event: String,
                                       start: Long,
                                       end: Long,
                                       description: String
                                     )

object WeatherClientResponseCurrent:
  given Decoder[WeatherClientResponseCurrent] = Decoder.derived[WeatherClientResponseCurrent]

  given Encoder[WeatherClientResponseCurrent] = Encoder.AsObject.derived[WeatherClientResponseCurrent]

object WeatherClientResponseCurrentWeather:
  given Decoder[WeatherClientResponseCurrentWeather] = Decoder.derived[WeatherClientResponseCurrentWeather]

  given Encoder[WeatherClientResponseCurrentWeather] = Encoder.AsObject.derived[WeatherClientResponseCurrentWeather]

object WeatherClientResponseAlert:
  given Decoder[WeatherClientResponseAlert] = Decoder.derived[WeatherClientResponseAlert]

  given Encoder[WeatherClientResponseAlert] = Encoder.AsObject.derived[WeatherClientResponseAlert]


object WeatherClientResponse:
  given Decoder[WeatherClientResponse] = Decoder.derived[WeatherClientResponse]

  given [F[_] : Concurrent]: EntityDecoder[F, WeatherClientResponse] = jsonOf

  given Encoder[WeatherClientResponse] = Encoder.AsObject.derived[WeatherClientResponse]

  given [F[_]]: EntityEncoder[F, WeatherClientResponse] = jsonEncoderOf

case class WeatherClientErrorResponse(throwable: Throwable) extends RuntimeException