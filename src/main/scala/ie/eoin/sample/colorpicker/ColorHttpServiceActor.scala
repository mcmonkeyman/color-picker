package ie.eoin.sample.colorpicker

import akka.actor.Actor
import spray.util.LoggingContext
import spray.routing._
import spray.http._
import spray.http.StatusCodes._
import spray.httpx.unmarshalling._
import spray.httpx.marshalling._
import spray.httpx.SprayJsonSupport._
import spray.json._
import spray.json.DefaultJsonProtocol._
import com.typesafe.config.ConfigFactory
import ie.eoin.sample.colorpicker.model._
import ie.eoin.sample.colorpicker.service._
import ie.eoin.sample.colorpicker.exception._
import ie.eoin.sample.colorpicker.serialization._
import ie.eoin.sample.colorpicker.serialization.ClientIdJsonImplicits._
import ie.eoin.sample.colorpicker.serialization.ColorJsonImplicits._
import ie.eoin.sample.colorpicker.serialization.ColorValueJsonImplicits._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class ColorHttpServiceActor extends Actor with ColorHttpService {

  implicit def colorExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: ColorServiceException =>
        requestUri { uri =>
          log.warning("Request to {} could not be handled normally", uri)
          complete(InternalServerError, e.message)
        }
    }

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait ColorHttpService extends HttpService {

  val config = ConfigFactory.load().getConfig("colorservice")
  val timeout = config.getInt("timeout")
  val queueSize  = config.getInt("queuesize")

  val colorService = new ColorService(queueSize, timeout)
  val sessionService = new ClientService()

  val myRoute =
    path("color" / Segment) { id =>
      get {
        complete {
          colorService.checkOutColor(ClientId(id))
        }
      }
    } ~
    path("color" /  Segment) { id =>
      post {
        complete {
          colorService.saveCheckedOutColor(ClientId(id))
        }
      }
    } ~
    path("color" / "latest" / Segment) { id =>
      get {
        complete(colorService.showLastSavedColor(ClientId(id)))
      }
    } ~
    path("uuid") {
      get {
        complete {
          sessionService.generateClientId
        }
      }
    }
}
