package com.github.kmruiz.doa.webApplication.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

@Controller
class HealthCheckController {
  @RequestMapping(Array("/healthcheck"))
  @ResponseBody
  def healthcheck = "OK"
}
