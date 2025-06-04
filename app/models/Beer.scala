package models

import play.api.libs.json.{Json, OFormat}

/**
 * Represents a Beer entity with various attributes.
 *
 * @param id Unique identifier for the beer.
 * @param name Name of the beer.
 * @param firstBrewed The date or period when the beer was first brewed.
 * @param description Detailed description of the beer.
 * @param imageUrl Optional URL to an image of the beer.
 * @param abv Alcohol by Volume percentage.
 * @param ph pH level of the beer.
 */
case class Beer(
                 id: Int,
                 name: String,
                 firstBrewed: String,
                 description: String,
                 imageUrl: Option[String],
                 abv: Double,
                 ph: Double
               )

object Beer {
  implicit val beerFormat: OFormat[Beer] = Json.format[Beer]
}
