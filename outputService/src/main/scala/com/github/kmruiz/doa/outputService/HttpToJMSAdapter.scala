package com.github.kmruiz.doa.outputService

import java.net.URL

import org.apache.camel.{CamelContext, ExchangePattern}
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder

case class HttpToJMSAdapter(camelContext: CamelContext) extends ScalaRouteBuilder(camelContext) {
  s"netty4-http:http://0.0.0.0:8090?throwExceptionOnFailure=false&send503whenSuspended=true&matchOnUriPrefix=true&urlDecodeHeaders=true".process { exchange =>
    val camelUrl = exchange.getIn().getHeader("Host").toString
    val host = new URL("http://" + camelUrl).getHost
    exchange.getIn().setHeader("JMSTarget", host)
    exchange
  }.setExchangePattern(ExchangePattern.InOut).recipients(simple("activemq:${header.JMSTarget}"))
}
