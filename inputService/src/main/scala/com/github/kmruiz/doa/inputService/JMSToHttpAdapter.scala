package com.github.kmruiz.doa.inputService

import org.apache.camel.{CamelContext, ExchangePattern}
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder

case class JMSToHttpAdapter(sources: Seq[String], camelContext: CamelContext) extends ScalaRouteBuilder(camelContext) {
    sources.foreach { source =>
      s"jms:queue:$source".setExchangePattern(ExchangePattern.InOut)
          .to("ahc:http://127.0.0.1:8080?bridgeEndpoint=true&throwExceptionOnFailure=false")
    }
}
