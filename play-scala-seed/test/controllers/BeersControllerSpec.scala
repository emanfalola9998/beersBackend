package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json
import models.Beer
import repositories.BeersRepository

import scala.concurrent.Future

class BeersControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "BeersController GET /beers" should {
    "return a list of beers" in {
      val controller = inject[BeersController]
      val result = controller.listBeers.apply(FakeRequest(GET, "/beers"))

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  "BeersController POST /beers" should {
    "create a new beer" in {
      val controller = inject[BeersController]
      val newBeer = Beer(0, "Test Beer", "2023", "A test beer", None, 5.0, 4.0)
      val request = FakeRequest(POST, "/beers")
        .withJsonBody(Json.toJson(newBeer))
        .withHeaders("Content-Type" -> "application/json")

      val result = controller.createBeer.apply(request)

      status(result) mustBe CREATED
      contentType(result) mustBe Some("application/json")
    }
  }

  // Additional tests for GET /beers/:id, PUT /beers/:id, DELETE /beers/:id

  "BeersController GET /beers/:id" should {
    "return a specific beer" in {
      val controller = inject[BeersController]
      val beerId = 1
      val result = controller.getBeer(beerId).apply(FakeRequest(GET, s"/beers/$beerId"))

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "return NotFound for non-existing beer" in {
      val controller = inject[BeersController]
      val beerId = 9999
      val result = controller.getBeer(beerId).apply(FakeRequest(GET, s"/beers/$beerId"))

      status(result) mustBe NOT_FOUND
      contentType(result) mustBe Some("application/json")
    }
  }

  "BeersController PUT /beers/:id" should {
    "update an existing beer" in {
      val controller = inject[BeersController]
      val beerId = 1
      val updatedBeer = Beer(beerId, "Updated Beer", "2024", "An updated beer", Some("http://example.com/image.png"), 6.0, 4.5)
      val request = FakeRequest(PUT, s"/beers/$beerId")
        .withJsonBody(Json.toJson(updatedBeer))
        .withHeaders("Content-Type" -> "application/json")

      val result = controller.updateBeer(beerId).apply(request)

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }

    "return NotFound when updating a non-existing beer" in {
      val controller = inject[BeersController]
      val beerId = 9999
      val updatedBeer = Beer(beerId, "Non-Existent Beer", "2024", "This beer does not exist", None, 6.0, 4.5)
      val request = FakeRequest(PUT, s"/beers/$beerId")
        .withJsonBody(Json.toJson(updatedBeer))
        .withHeaders("Content-Type" -> "application/json")

      val result = controller.updateBeer(beerId).apply(request)

      status(result) mustBe NOT_FOUND
      contentType(result) mustBe Some("application/json")
    }
  }

  "BeersController DELETE /beers/:id" should {
    "delete an existing beer" in {
      val controller = inject[BeersController]
      val beerId = 1
      val result = controller.deleteBeer(beerId).apply(FakeRequest(DELETE, s"/beers/$beerId"))

      status(result) mustBe NO_CONTENT
    }

    "return NotFound when deleting a non-existing beer" in {
      val controller = inject[BeersController]
      val beerId = 9999
      val result = controller.deleteBeer(beerId).apply(FakeRequest(DELETE, s"/beers/$beerId"))

      status(result) mustBe NOT_FOUND
      contentType(result) mustBe Some("application/json")
    }
  }
}
