package ie.eoin.sample.colorpicker.model.collections

import concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import ie.eoin.sample.colorpicker.model._
import ie.eoin.sample.colorpicker.exception._
import ie.eoin.sample.colorpicker.model.collections._

class CheckOutManager(sourceCollection: RegeneratingColorCollection, timeout: Int) {
  var checkedOut:  scala.collection.mutable.Map[SessionId, Color] =  scala.collection.mutable.Map()

  def checkOut(id:SessionId) = {
    if(clientHasColorCheckedOut(id)) {
      throw new ColorServiceException("Can't check out a color, this client currently already has a color checked out. A client can only check out one color at a time.")
    } else {
      val color = sourceCollection.getNextRandomColor()  
      checkedOut.put(id, color)
      releaseColorAfterTimeout(id, color)    
      color
    }
  }

  def get(id: SessionId) = {
    if(clientHasNoColorCheckedOut(id)){
      throw new ColorServiceException("Can't set a color, this client does not have a color checked out.")
    } else {
      checkedOut.get(id).get
    }
  }

  private def clientHasColorCheckedOut(id: SessionId): Boolean = {
    checkedOut.contains(id)
  }

  private def clientHasNoColorCheckedOut(id: SessionId) = {
    !clientHasColorCheckedOut(id)
  }
  
  private def releaseColorAfterTimeout(id:SessionId, color: Color): Unit = {
    Future {
      Thread.sleep(timeout)
      checkedOut.get(id) match {
        case Some(x) => {
          checkedOut -= id  
          sourceCollection.returnUnusedColor(color)
        }
        case None => {}
      }
    }
  }
}
