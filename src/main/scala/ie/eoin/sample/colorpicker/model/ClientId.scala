package ie.eoin.sample.colorpicker.model

case class ClientId(val id: String) {
  override def equals(o: Any) = o match {
    case that: ClientId => that.id.equalsIgnoreCase(this.id)
    case _ => false
  }
  override def hashCode = id.toUpperCase.hashCode
}
