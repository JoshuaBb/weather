import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import io.circe._
import io.circe.parser._
import com.jbb.weather.client.model.WeatherClientResponse

class WeatherClientResponseSpec extends AnyFlatSpec with Matchers {

  "WeatherClientResponse" should "deserialize json file" in {
    val json = parse(scala.io.Source.fromResource("weather-3.0.json").mkString)

    val decodedResponse = json.flatMap(_.as[WeatherClientResponse])

    decodedResponse match {
      case Left(error) =>
        fail(s"Unable to parse json file: $error")
      case Right(response) =>
        // Add assertions here to validate the decoded response
        response shouldBe a[WeatherClientResponse] // Example assertion
    }
  }
}