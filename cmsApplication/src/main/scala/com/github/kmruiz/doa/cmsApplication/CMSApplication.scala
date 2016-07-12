package com.github.kmruiz.doa.cmsApplication

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kmruiz.doa.cmsApplication.application.ContentManagementSystem
import com.github.kmruiz.doa.cmsApplication.controller.CMSController
import com.github.kmruiz.doa.cmsApplication.domain.card.CardService
import com.github.kmruiz.doa.cmsApplication.domain.client.ClientService
import com.github.kmruiz.doa.cmsApplication.domain.user.{Administrator, BatchProcess, UserService}
import com.github.kmruiz.doa.cmsApplication.infrastructure.card.AwesomeCardRepository
import com.github.kmruiz.doa.cmsApplication.infrastructure.client.AwesomeClientRepository
import com.github.kmruiz.doa.cmsApplication.infrastructure.user.AwesomeUserRepository
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import com.fasterxml.jackson.module.scala.DefaultScalaModule

@EnableAutoConfiguration
class CMSApplication {
  @Bean
  def objectMapper() = {
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper
  }

  @Bean
  def cmsController() = CMSController(
    ContentManagementSystem(
      UserService(
        new AwesomeUserRepository(
          Map(
            "administrator" -> Administrator("administrator", "123456"),
            "web-application" -> BatchProcess("web-application")
          )
        )
      ), ClientService(
        new AwesomeClientRepository()
      ), CardService(
        new AwesomeCardRepository()
      )
    )
  )
}

object CMSApplication {
  def main(args: Array[String]) {
    SpringApplication.run(classOf[CMSApplication], args: _*)
  }
}