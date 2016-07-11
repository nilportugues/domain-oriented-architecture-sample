package com.github.kmruiz.doa.cmsApplication

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration

@EnableAutoConfiguration
class CMSApplication

object CMSApplication {
  def main(args: Array[String]) {
    SpringApplication.run(classOf[CMSApplication], args: _*)
  }
}