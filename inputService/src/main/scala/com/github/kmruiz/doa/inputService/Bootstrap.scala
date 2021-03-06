package com.github.kmruiz.doa.inputService

import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.camel.component.jms.JmsComponent
import org.apache.camel.impl.DefaultCamelContext

object Bootstrap {
  def main(args: Array[String]) {
    val context = new DefaultCamelContext()
    val connectionFactory = new ActiveMQConnectionFactory("tcp://queue.service:61616")
    context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory))
    context.addRoutes(JMSToHttpAdapter(inputSourceEnv.split(",").filterNot(_.isEmpty), context))
    context.setAllowUseOriginalMessage(false)
    context.setStreamCaching(true)
    context.start()
  }

  def inputSourceEnv = Option(System.getenv("INPUT_SOURCES")).getOrElse("")
}
