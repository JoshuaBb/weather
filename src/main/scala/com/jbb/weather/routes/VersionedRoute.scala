package com.jbb.weather.routes

import org.http4s.Uri

trait VersionedRoute:
  def version: String
  def versionBase: Uri.Path =  Uri.Path.Root / version

trait VersionRouteV1 extends VersionedRoute:
  override def version: String = "v1"