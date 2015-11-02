package ie.eoin.sample.colorpicker.service

import concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import ie.eoin.sample.colorpicker.model._
import ie.eoin.sample.colorpicker.model.collections._
import ie.eoin.sample.colorpicker.exception._

class ColorService(val numberOfColors:Int) {
  var colorCollection = new RegeneratingColorCollection(numberOfColors) 
  var checkedOutColors:  scala.collection.mutable.Map[SessionId, Color] =  scala.collection.mutable.Map()
  var savedColorCollection = new SavedColorCollection()
  val timeout = 15000

  def checkOutColor(id: SessionId): Color = {
    if(clientHasColorCheckedOut(id)) {
      throw new ColorServiceException("Can't check out a color, this client currently already has a color checked out. A client can only check out one color at a time.")
    } else {
      val color = colorCollection.getNextRandomColor()  
      checkedOutColors.put(id, color)
      releaseColorAfterTimeout(id, color)      
      color
    }
  }

  def saveCheckedOutColor(id: SessionId): Color = {
    if(clientHasColorCheckedOut(id)) {
      val color = checkedOutColors.get(id).get
      savedColorCollection.add(id, color)
      color 
    } else {
      throw new ColorServiceException("Can't set a color, this client does not have a color checked out.")
    }
  }

  def showLastSavedColor(id: SessionId): Option[Color]  = {
    savedColorCollection.showLastSavedColor(id)   
  }

  private def clientHasColorCheckedOut(id: SessionId): Boolean = {
    checkedOutColors.contains(id)
  }

  private def releaseColorAfterTimeout(id:SessionId, color: Color): Unit = {
    Future {
      Thread.sleep(timeout)
      checkedOutColors.get(id) match {
        case Some(x) => {
          checkedOutColors -= id  
          colorCollection.returnUnusedColor(color)
        }
        case None => {}
      }
    }
  }
}


