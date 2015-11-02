package ie.eoin.sample.colorpicker.service

import ie.eoin.sample.colorpicker.model._
import ie.eoin.sample.colorpicker.model.collections._
import ie.eoin.sample.colorpicker.exception._

class ColorService(val numberOfColors:Int, val timeout: Int) {
  var colorCollection = new RegeneratingColorCollection(numberOfColors) 
  var savedColorCollection = new SavedColorCollection()
  var checkOutManager = new CheckOutManager(colorCollection, timeout)   

  def checkOutColor(id: ClientId): Color = {
    checkOutManager.checkOut(id)
  }

  def saveCheckedOutColor(id: ClientId): Color = {
    val color = checkOutManager.get(id)
    savedColorCollection.add(id, color)
    color 
  }

  def showLastSavedColor(id: ClientId): Option[Color]  = {
    savedColorCollection.showLastSavedColor(id)   
  }
}


