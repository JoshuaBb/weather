package com.jbb.weather.client

import cats.effect.Concurrent
import com.jbb.weather.client.model.{WeatherClientErrorResponse, WeatherClientResponse}
import org.http4s.client.Client
import org.http4s.Method.GET
import cats.syntax.all.*
import com.jbb.weather.WeatherConfig
import org.http4s.Uri
import org.http4s.client.dsl.Http4sClientDsl
import org.typelevel.log4cats.Logger

trait WeatherClient[F[_]]:
  def get(lat: Double, lon: Double): F[WeatherClientResponse]

object WeatherClient:
  def impl[F[_] : Concurrent : Logger](c: Client[F], weatherConfig: WeatherConfig): WeatherClient[F] = (lat: Double, lon: Double) =>
    val dsl = new Http4sClientDsl[F] {}
    val excluded = Set("minutely", "hourly", "daily").mkString(",")
    import dsl.*
    val uriString = s"https://api.openweathermap.org/data/3.0/onecall?lat=$lat&lon=$lon&excluded=$excluded&appid=${weatherConfig.apiKey}"
    // Since I am more or less hardcoding the entire string having a .get is probably fine.
    // For production code I would look on how to handle this a bit better
    val uri = Uri.fromString(uriString).toOption.get
    val request = GET(uri)
    Logger[F].info(s"Requesting weather information from: $uri") *>
      c.expect[WeatherClientResponse](request)
        .adaptError { case t =>
          println(t.toString)
          WeatherClientErrorResponse(t)
        }
        .flatTap(_ => Logger[F].info(s"Successfully retrieved weather information for lat: $lat, lon: $lon"))
    
    
  
