package com.jbb.weather.routes.query

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class LonMatcherSpec extends AnyFlatSpec with Matchers {

  "LonMatcher" should "decode query param correctly" in {
    val queryParams = Map("lon" -> Seq("2.0"))
    val decodedQueryParam = LonMatcher.unapply(queryParams)
    decodedQueryParam shouldBe Some(2.0)
  }
  "LonMatcher" should "ignore conversion when invalid type" in {
    val queryParams = Map("lon" -> Seq("Not a double"))
    val decodedQueryParam = LonMatcher.unapply(queryParams)
    decodedQueryParam shouldBe None
  }
  "LonMatcher" should "return None when missing parameter entirely" in {
    val queryParams = Map.empty[String, Seq[String]]
    val decodedQueryParam = LonMatcher.unapply(queryParams)
    decodedQueryParam shouldBe None
  }
}
