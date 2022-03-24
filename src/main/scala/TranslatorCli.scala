import org.apache.http.HttpStatus
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils

object TranslatorCli extends App {

  def parse(response: CloseableHttpResponse): String = {
    val entity = EntityUtils.toString(response.getEntity)
    val json = ujson.read(entity)
    json("rows").arr
      .map(item => item("value"))
      .map(item => (item("Word"), item("Text")))
      .map(tuple => tuple._1 + " - " + tuple._2 + "\n")
      .reduceLeft(_ + _)
  }

  def handleGetRequest = {
    val httpGet = new HttpGet(s"$url$word")
    val client = HttpClientBuilder.create().build()
    val response = client.execute(httpGet)
    response.getStatusLine.getStatusCode match {
      case HttpStatus.SC_OK =>
        println(parse(response))
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
