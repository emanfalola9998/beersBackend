package controllers

import javax.inject._
import play.api.mvc._
import repositories.BeersRepository
import models.Beer
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._

@Singleton
class BeersController @Inject()(cc: ControllerComponents, beersRepository: BeersRepository)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  /** GET /beers - List all beers */
  def listBeers: Action[AnyContent] = Action.async { implicit request =>
    beersRepository.list().map { beers =>
      Ok(Json.toJson(beers))
    }
  }

  /** GET /beers/:id - Get a beer by ID */
  def getBeer(id: Int): Action[AnyContent] = Action.async { implicit request =>
    beersRepository.findById(id).map {
      case Some(beer) => Ok(Json.toJson(beer))
      case None => NotFound(Json.obj("error" -> s"Beer with id $id not found"))
    }
  }

  /** POST /beers - Create a new beer */
  def createBeer: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[Beer].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "Invalid JSON"))),
      beer => {
        beersRepository.create(beer).map { createdBeer =>
          Created(Json.toJson(createdBeer))
        }
      }
    )
  }

  /** PUT /beers/:id - Update an existing beer */
  def updateBeer(id: Int): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[Beer].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "Invalid JSON"))),
      beer => {
        beersRepository.update(id, beer).map { updatedRows =>
          if (updatedRows > 0) Ok(Json.toJson(beer.copy(id = id)))
          else NotFound(Json.obj("error" -> s"Beer with id $id not found"))
        }
      }
    )
  }

  /** DELETE /beers/:id - Delete a beer */
  def deleteBeer(id: Int): Action[AnyContent] = Action.async { implicit request =>
    beersRepository.delete(id).map { deletedRows =>
      if (deletedRows > 0) NoContent
      else NotFound(Json.obj("error" -> s"Beer with id $id not found"))
    }
  }
}
