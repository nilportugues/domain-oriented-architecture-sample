package com.github.kmruiz.doa.webApplication

import com.github.kmruiz.doa.webApplication.controller.HealthCheckController
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean

@EnableAutoConfiguration
class WebApplication {
  @Bean def healthCheckController = new HealthCheckController()
}

object WebApplication {
  def main(args: Array[String]) {
    SpringApplication.run(classOf[WebApplication], args: _*)
  }
}