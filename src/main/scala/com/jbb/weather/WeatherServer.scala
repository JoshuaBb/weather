package com.jbb.weather

import cats.effect.Async
import cats.syntax.all.*
import com.jbb.weather.client.WeatherClient
import com.jbb.weather.routes.WeatherRoutes
import com.jbb.weather.routes.v1.GetWeatherResponses
import fs2.io.net.Network
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger
import org.typelevel.log4cats.{Logger => AppLogger}
import org.typelevel.log4cats.slf4j.Slf4jLogger

object WeatherServer:

  def run[F[_]: Async: Network](appConfig: AppConfig): F[Nothing] = {
    given AppLogger[F] = Slf4jLogger.getLogger[F]

    val serverConfig = appConfig.server

    for {
      client <- EmberClientBuilder.default[F].build
      weatherClient = WeatherClient.impl[F](client, appConfig.clients.weather)
      helloWorldAlg = HelloWorld.impl[F]
      getWeatherResponse = GetWeatherResponses.impl[F](weatherClient)

      httpApp = (
        WeatherRoutes.helloWorldRoutes[F](helloWorldAlg) <+>
        WeatherRoutes.getWeatherRoutes[F](getWeatherResponse)
      ).orNotFound

      finalHttpApp = Logger.httpApp(true, true)(httpApp)



      _ <-
        EmberServerBuilder.default[F]
          .withHost(serverConfig.host)
          .withPort(serverConfig.port)
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
