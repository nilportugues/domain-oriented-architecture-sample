package com.github.kmruiz.doa.webApplication.controller

import com.github.kmruiz.doa.cmsApplication.al.{CMSControllerClient, CreateClientRequest}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, ResponseBody}

case class AccessBetaRequest(username: String)

@Controller
class BetaController(cmsContrlolerClient: CMSControllerClient) {
  @RequestMapping(value = Array("/beta"), method = Array(POST))
  @ResponseBody
  def accessBeta(@RequestBody request: AccessBetaRequest) = {
    cmsContrlolerClient.createClient(CreateClientRequest("web-application", request.username))
  }
}
