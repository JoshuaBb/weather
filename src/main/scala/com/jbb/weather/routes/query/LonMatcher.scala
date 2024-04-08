package com.jbb.weather.routes.query

import org.http4s.dsl.impl.QueryParamDecoderMatcher

object LonMatcher extends QueryParamDecoderMatcher[Double]("lon")
