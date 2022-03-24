import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils

object TranslatorCli extends App {

  def handleGetRequest = {
    val httpGet = new HttpGet(s"$url$word")
    val client = HttpClientBuilder.create().build()
    val response = client.execute(httpGet)
    response.getStatusLine.getStatusCode match {
      case HttpStatus.SC_OK => {
        println(EntityUtils.toString(response.getEntity))
      }
    }
  }

  require(args.length >= 1, "Please pass word to translate!")
  val word = args.head
  val command = "get"
  val url = "https://translate.ge/api/"

  command match {
    case "get" => handleGetRequest
  }

}
