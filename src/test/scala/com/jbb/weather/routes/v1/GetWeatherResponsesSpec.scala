package com.jbb.weather.routes.v1

import cats.effect.IO
import com.jbb.weather.client.WeatherClient
import com.jbb.weather.client.model.{WeatherClientResponse, WeatherClientResponseAlert, WeatherClientResponseCurrent, WeatherClientResponseCurrentWeather}
import com.jbb.weather.model.v1.GetWeatherResponse
import munit.CatsEffectSuite
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

class GetWeatherResponsesSpec extends CatsEffectSuite:

  val response = WeatherClientResponse(
      current = WeatherClientResponseCurrent(
        temp = 300.00,
        weather = Seq(
          WeatherClientResponseCurrentWeather(
            main = "Thunderstorm"
          )
        )
    ),
    alerts = Option(Seq.empty[WeatherClientResponseAlert])
  )

  test("GetWeatherResponses.get returns the correct message") {
    assertIO(retGetWeather.get(1.0, 2.0), GetWeatherResponse(response))
  }
  
  private[this] def retGetWeather: GetWeatherResponses[IO] =
    given Logger[IO] = Slf4jLogger.getLogger[IO]
    val getWeather = new WeatherClient[IO] {
      def get(lat: Double, lon: Double): IO[WeatherClientResponse] =
        IO.pure(response)
    }
    GetWeatherResponses.impl[IO](getWeather)
    


