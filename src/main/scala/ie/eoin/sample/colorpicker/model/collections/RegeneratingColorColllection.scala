package ie.eoin.sample.colorpicker.model.collections

import scala.collection.mutable.Queue
import ie.eoin.sample.colorpicker.model.{Color, ColorValue}

class RegeneratingColorColllection(val initialSize: Int) {

  val minimumAllowedSize = 20
  var queue: Queue[Color] = new Queue()
  var r = new scala.util.Random

  fillCollection()

  def getNextRandomColor(): Color = {
    if(timeToRefill){
     fillCollection()
    }
    queue.dequeue
  }

  def returnUnusedColor(color: Color) = {
    queue.enqueue(color)
  }
 
  private def timeToRefill(): Boolean = {
    queue.size < minimumAllowedSize
  }

  private def fillCollection(): Unit = {
    val numberToFill = initialSize - minimumAllowedSize
    for(i <- 1 to numberToFill) {
      queue.enqueue(generateColor())
    }
  }

  private def generateColor(): Color = {
    val colorValues = 1 to 3 map { _ => ColorValue(r.nextInt(225)) }
    Color(colorValues(0), colorValues(1), colorValues(2))
  }
}
