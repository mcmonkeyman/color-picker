package ie.eoin.sample.colorpicker.serialization

import spray.json._
import ie.eoin.sample.colorpicker.model.SessionId

object SessionIdJsonImplicits extends DefaultJsonProtocol {
  implicit val impSessionId = jsonFormat1(SessionId)
}
