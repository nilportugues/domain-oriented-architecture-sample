package com.github.kmruiz.doa.webApplication.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kmruiz.doa.cmsApplication.al.CreateClientRequest
import com.github.kmruiz.doa.cmsApplication.al.infrastructure.HttpCMSControllerClient
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

@Controller
class HealthCheckController(objectMapper: ObjectMapper) {

  @RequestMapping(Array("/healthcheck"))
  @ResponseBody
  def healthcheck = HttpCMSControllerClient("http://cms.service:9000", objectMapper).createClient(CreateClientRequest("web-application", "some-test-client"))
}
