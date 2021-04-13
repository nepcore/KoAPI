package koapi.logging

import org.slf4j.{Logger => Slf4jLogger, LoggerFactory}

trait Logger {
  protected val logger: Slf4jLogger = LoggerFactory.getLogger(this.getClass)
}
