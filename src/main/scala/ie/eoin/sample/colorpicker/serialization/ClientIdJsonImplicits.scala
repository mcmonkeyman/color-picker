package ie.eoin.sample.colorpicker.serialization

import spray.json._
import ie.eoin.sample.colorpicker.model.ClientId

object ClientIdJsonImplicits extends DefaultJsonProtocol {
  implicit val impClientId = jsonFormat1(ClientId)
}
