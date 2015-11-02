package ie.eoin.sample.colorpicker.model

object ColorValue {
  def apply(value:Int): ColorValue = {
    require(value >= 0 && value<256, "the number must be between 0 and 255")
    new ColorValue(value)
  }
}

class ColorValue private(val value:Int) {
  override def toString = {
    value.toString 
  }
}


