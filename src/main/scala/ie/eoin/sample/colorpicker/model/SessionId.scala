package ie.eoin.sample.colorpicker.model

case class SessionId(val sessionId: String) {
  override def equals(o: Any) = o match {
    case that: SessionId => that.sessionId.equalsIgnoreCase(this.sessionId)
    case _ => false
  }
  override def hashCode = sessionId.toUpperCase.hashCode
}
