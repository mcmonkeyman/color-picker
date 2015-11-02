package ie.eoin.sample.colorpicker.serialization

import spray.json._
import ie.eoin.sample.colorpicker.serialization.ColorValueJsonImplicits._
import ie.eoin.sample.colorpicker.model.Color

object ColorJsonImplicits extends DefaultJsonProtocol {
  implicit val impColor = jsonFormat3(Color.apply)
}



