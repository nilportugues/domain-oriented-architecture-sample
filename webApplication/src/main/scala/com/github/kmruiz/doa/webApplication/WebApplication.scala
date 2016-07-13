package com.github.kmruiz.doa.webApplication

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.github.kmruiz.doa.cmsApplication.al.CMSControllerClient
import com.github.kmruiz.doa.cmsApplication.al.infrastructure.HttpCMSControllerClient
import com.github.kmruiz.doa.webApplication.controller.BetaController
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean

@EnableAutoConfiguration
class WebApplication {
  @Bean
  def objectMapper() = {
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper
  }

  @Bean
  def cmsControllerClient(objectMapper: ObjectMapper): CMSControllerClient = {
    HttpCMSControllerClient("http://cms.service:9000", objectMapper)
  }

  @Bean
  def accessBetaController(cmsControllerClient: CMSControllerClient) = new BetaController(cmsControllerClient)
}

object WebApplication {
  def main(args: Array[String]) {
    SpringApplication.run(classOf[WebApplication], args: _*)
  }
}