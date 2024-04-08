package com.jbb.weather.routes.v1

import cats.effect.Concurrent
import com.jbb.weather.model.v1.GetWeatherResponse
import cats.syntax.all.*
import com.jbb.weather.client.WeatherClient
import org.typelevel.log4cats.Logger

trait GetWeatherResponses[F[_]]:
  def get(lat: Double, lon: Double): F[GetWeatherResponse]

object GetWeatherResponses:
  def impl[F[_] : Concurrent : Logger](c: WeatherClient[F]): GetWeatherResponses[F] = (lat: Double, lon: Double) => 
    Logger[F].info(s"Requesting weather information for lat: $lat, lon: $lon") *> 
      c.get(lat, lon).flatTap(_ => Logger[F].info("Successfully retrieved weather information"))
        .map(GetWeatherResponse(_))


