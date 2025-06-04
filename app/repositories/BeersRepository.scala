package repositories

import javax.inject.{Inject, Singleton}
import models.Beer
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BeersRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private class BeersTable(tag: Tag) extends Table[Beer](tag, "beers") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def firstBrewed = column[String]("first_brewed")
    def description = column[String]("description")
    def imageUrl = column[Option[String]]("image_url")
    def abv = column[Double]("abv")
    def ph = column[Double]("ph")

    def * = (id, name, firstBrewed, description, imageUrl, abv, ph) <> ((Beer.apply _).tupled, Beer.unapply)
  }

  private val beers = TableQuery[BeersTable]

  /** Retrieve all beers */
  def list(): Future[Seq[Beer]] = db.run(beers.result)

  /** Find a beer by ID */
  def findById(id: Int): Future[Option[Beer]] = db.run(beers.filter(_.id === id).result.headOption)

  /** Create a new beer */
  def create(beer: Beer): Future[Beer] = {
    val beerToInsert = beer.copy(id = 0) // ID will be auto-generated
    db.run((beers returning beers.map(_.id) into ((beer, id) => beer.copy(id = id))) += beerToInsert)
  }

  /** Update an existing beer */
  def update(id: Int, updatedBeer: Beer): Future[Int] = {
    db.run(beers.filter(_.id === id).update(updatedBeer.copy(id = id)))
  }

  /** Delete a beer */
  def delete(id: Int): Future[Int] = {
    db.run(beers.filter(_.id === id).delete)
  }
}
