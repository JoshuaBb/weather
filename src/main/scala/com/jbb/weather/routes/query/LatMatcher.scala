package com.jbb.weather.routes.query

import org.http4s.dsl.impl.QueryParamDecoderMatcher

object LatMatcher extends QueryParamDecoderMatcher[Double]("lat")
