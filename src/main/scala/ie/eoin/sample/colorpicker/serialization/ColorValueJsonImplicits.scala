package ie.eoin.sample.colorpicker.serialization

import spray.json._
import spray.json.DefaultJsonProtocol._
import ie.eoin.sample.colorpicker.model.ColorValue

object ColorValueJsonImplicits extends DefaultJsonProtocol {
  implicit object ColorValueJsonFormat extends RootJsonFormat[ColorValue] { 
    def write(obj: ColorValue): JsValue = {
      JsObject(
        ("colorValue", JsString(obj.value.toString))
      )
    }

    def read(json: JsValue): ColorValue = json match {
      case JsObject(fields)
        if fields.isDefinedAt("colorValue")  =>
          ColorValue(fields("colorValue").convertTo[Int])
      case _ => deserializationError("Not a ColorValue")
    }
  }
}

