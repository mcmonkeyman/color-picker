package ie.eoin.sample.colorpicker.service

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import ie.eoin.sample.colorpicker.model.SessionId

class SessionService {
  def generateUserSessionId(): SessionId = {
      // throws UnsupportedEncodingException
    val uid = new java.rmi.server.UID().toString(); // guaranteed unique
    SessionId(URLEncoder.encode(uid,"UTF-8"))// encode any special chars
  }
}


