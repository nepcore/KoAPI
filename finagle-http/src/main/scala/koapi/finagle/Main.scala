package koapi.finagle

import com.typesafe.config.ConfigFactory

object Main extends App {
  private val config = ConfigFactory.load()
  val server = new FinagleHttpServer(config)
}
