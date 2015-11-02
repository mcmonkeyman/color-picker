package ie.eoin.sample.colorpicker.service

import java.net.URLEncoder
import ie.eoin.sample.colorpicker.model.ClientId

class ClientService {
  def generateClientId(): ClientId = {
    val uid = new java.rmi.server.UID().toString(); // guaranteed unique
    ClientId(URLEncoder.encode(uid,"UTF-8"))// encode any special chars
  }
}


