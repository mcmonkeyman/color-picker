package ie.eoin.sample.colorpicker.model.collections

import scala.collection.mutable.Queue
import ie.eoin.sample.colorpicker.model._

class SavedColorCollection {
  var savedColorCollection: Queue[(ClientId, Color)] = new Queue()

  def add(id:ClientId, color:Color) = {
    savedColorCollection.enqueue((id, color))  
    color
  }

  def showLastSavedColor(id: ClientId): Option[Color]  = {
    savedColorCollection.toList.reverse.find(_._1 == id) match {
      case Some(s) => Some(s._2)
      case _ => None
    } 
  }
}
