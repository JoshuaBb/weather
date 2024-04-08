package com.jbb.weather

import cats.effect.IO
import com.jbb.weather.model.{ConditionType, TempType}
import com.jbb.weather.model.v1.{GetWeatherResponse, GetWeatherResponseAlert}
import com.jbb.weather.routes.WeatherRoutes
import munit.CatsEffectSuite
import org.http4s.{Method, Request, Response, Status}
import org.http4s.implicits.uri
import com.jbb.weather.routes.v1.GetWeatherResponses

class WeatherRoutesSpec extends CatsEffectSuite:

  val response = GetWeatherResponse(
    tempType = TempType.COLD,
    conditions = Seq(ConditionType.Clouds),
    alerts = Seq.empty[GetWeatherResponseAlert]
  )

  test("GetWeather returns status code 200") {
    assertIO(retGetWeather.map(_.status) ,Status.Ok)
  }

  test("GetWeather returns the correct message") {
    assertIO(retGetWeather.flatMap(_.as[GetWeatherResponse]), response)
  }

  private[this] def retGetWeather: IO[Response[IO]] =
    val getHW = Request[IO](Method.GET, uri"/v1/weather?lat=2&lon=2")
    val getWeather = new GetWeatherResponses[IO]{
      def get(lat: Double, lon: Double): IO[GetWeatherResponse] =
        IO.pure(response)
    }

    WeatherRoutes.getWeatherRoutes(getWeather).orNotFound(getHW)
