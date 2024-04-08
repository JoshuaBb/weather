package com.jbb.weather.routes

import cats.effect.Sync
import cats.syntax.all.*
import com.jbb.weather.HelloWorld
import com.jbb.weather.routes.query.{LatMatcher, LonMatcher}
import com.jbb.weather.routes.v1.GetWeatherResponses
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object WeatherRoutes extends VersionRouteV1:

  def getWeatherRoutes[F[_]: Sync](a: GetWeatherResponses[F]): HttpRoutes[F] =
    val dsl = new Http4sDsl[F] {}
    import dsl.*
    HttpRoutes.of[F] {
      case GET ->  versionBase / "weather" :? LatMatcher(lat) +& LonMatcher(lon)  =>
        for {
          weatherData <- a.get(lat, lon)
          response <- Ok(weatherData)
        } yield response
    }

  def helloWorldRoutes[F[_] : Sync](H: HelloWorld[F]): HttpRoutes[F] =
    val dsl = new Http4sDsl[F] {}
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        for {
          greeting <- H.hello(HelloWorld.Name(name))
          resp <- Ok(greeting)
        } yield resp
    }