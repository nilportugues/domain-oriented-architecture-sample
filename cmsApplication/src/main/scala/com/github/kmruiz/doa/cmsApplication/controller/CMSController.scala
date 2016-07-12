package com.github.kmruiz.doa.cmsApplication.controller

import com.github.kmruiz.doa.cmsApplication.al.{CommentClientRequest, CreateClientRequest, LoginRequest}
import com.github.kmruiz.doa.cmsApplication.application.ContentManagementSystem
import com.github.kmruiz.doa.cmsApplication.domain.card.Card
import com.github.kmruiz.doa.cmsApplication.domain.client.Client
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._

@Controller
@RequestMapping(value = Array("/cms/"))
case class CMSController(cms: ContentManagementSystem) {
  @RequestMapping(value = Array("/login"), method = Array(RequestMethod.PUT))
  @ResponseBody
  def login(@RequestBody loginRequest: LoginRequest): Card = {
    cms.login(loginRequest.username, loginRequest.password)
  }

  @RequestMapping(value = Array("/client"), method = Array(RequestMethod.POST))
  @ResponseBody
  def createClient(@RequestBody createClientRequest: CreateClientRequest): Client = {
    cms.createClient(createClientRequest.origin, createClientRequest.client)
  }

  @RequestMapping(value = Array("/comment"), method = Array(RequestMethod.PUT))
  @ResponseBody
  def commentClient(@RequestBody commentClientRequest: CommentClientRequest): Client = {
    cms.commentClient(commentClientRequest.token, commentClientRequest.client, commentClientRequest.comment)
  }
}
