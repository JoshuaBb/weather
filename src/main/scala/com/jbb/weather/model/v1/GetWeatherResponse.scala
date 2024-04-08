package com.jbb.weather.model.v1

import cats.effect.Concurrent
import com.jbb.weather.client.model.{WeatherClientResponse, WeatherClientResponseAlert}
import com.jbb.weather.model.{ConditionType, TempType}
import io.circe.*
import org.http4s.*
import org.http4s.circe.*
import io.circe.generic.semiauto.*

import java.time.Instant

case class GetWeatherResponse(
                               tempType: TempType,
                               conditions: Seq[ConditionType],
                               alerts: Seq[GetWeatherResponseAlert]
                             )

case class GetWeatherResponseAlert(
                                     event: String,
                                     start: Instant,
                                     end: Instant,
                                     description: String,
                                     isActive: Boolean
                                   )
object GetWeatherResponseAlert:
  def apply(alert: WeatherClientResponseAlert): GetWeatherResponseAlert =
    val start = Instant.ofEpochSecond(alert.start)
    val end = Instant.ofEpochSecond(alert.end)
    GetWeatherResponseAlert(
      event = alert.event,
      start = Instant.ofEpochSecond(alert.start),
      end = Instant.ofEpochSecond(alert.end),
      description = alert.description,
      isActive = start.isBefore(Instant.now()) && end.isAfter(Instant.now())
    )
  given Decoder[GetWeatherResponseAlert] = Decoder.derived[GetWeatherResponseAlert]
  given Encoder[GetWeatherResponseAlert] = Encoder.AsObject.derived[GetWeatherResponseAlert]



object GetWeatherResponse:

  def apply(weatherClientResponse: WeatherClientResponse): GetWeatherResponse = {
    val mainConditionTypes = weatherClientResponse.current.weather.map(_.main match {
      case "Thunderstorm" => ConditionType.Thunderstorm
      case "Drizzle" => ConditionType.Drizzle
      case "Rain" => ConditionType.Rain
      case "Snow" => ConditionType.Snow
      case "Clouds" => ConditionType.Clouds
      case "Clear" => ConditionType.Clear
      case _ => ConditionType.Atmosphere
    })
    val kelvinTemp = weatherClientResponse.current.temp

    val tempType =
      if(kelvinTemp <= 272.039) TempType.COLD // 30 degrees f
      else if (kelvinTemp >= 294.261) TempType.WARM // 70 degrees f
      else TempType.MODERATE

    val alerts = weatherClientResponse
      .alerts
      .getOrElse(Seq.empty[WeatherClientResponseAlert])
      .map(GetWeatherResponseAlert(_))
    
    GetWeatherResponse(
      tempType = tempType,
      conditions = mainConditionTypes,
      alerts = alerts
    )
  }

  given Decoder[GetWeatherResponse] = Decoder.derived[GetWeatherResponse]
  given [F[_] : Concurrent]: EntityDecoder[F, GetWeatherResponse] = jsonOf
  given Encoder[GetWeatherResponse] = Encoder.AsObject.derived[GetWeatherResponse]
  given [F[_]]: EntityEncoder[F, GetWeatherResponse] = jsonEncoderOf

final case class GetWeatherResponseError(e: Throwable) extends RuntimeException
