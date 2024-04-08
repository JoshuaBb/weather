package com.jbb.weather.routes.query

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class LatMatcherSpec extends AnyFlatSpec with Matchers {

  "LatMatcher" should "decode query param correctly" in {
    val queryParams = Map("lat" -> Seq("2.0"))
    val decodedQueryParam = LatMatcher.unapply(queryParams)
    decodedQueryParam shouldBe Some(2.0)
  }
  "LatMatcher" should "ignore conversion when invalid type" in {
    val queryParams = Map("lat" -> Seq("Not a double"))
    val decodedQueryParam = LatMatcher.unapply(queryParams)
    decodedQueryParam shouldBe None
  }
  "LatMatcher" should "return None when missing parameter entirely" in {
    val queryParams = Map.empty[String, Seq[String]]
    val decodedQueryParam = LatMatcher.unapply(queryParams)
    decodedQueryParam shouldBe None
  }
}
