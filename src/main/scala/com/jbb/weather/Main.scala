package com.jbb.weather

import cats.effect.{IO, IOApp}
import pureconfig.ConfigSource

object Main extends IOApp.Simple:
  val run = ConfigSource.default.load[Config] match {
    case Right(value) =>  WeatherServer.run[IO](value.app)
    case Left(error) =>
      IO.raiseError(new RuntimeException("Failed to initialize config"))
  }
