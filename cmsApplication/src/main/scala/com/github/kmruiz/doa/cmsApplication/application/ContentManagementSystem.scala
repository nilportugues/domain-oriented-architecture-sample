package com.github.kmruiz.doa.cmsApplication.application

import com.github.kmruiz.doa.cmsApplication.domain.card.{Card, CardService}
import com.github.kmruiz.doa.cmsApplication.domain.client.{Client, ClientService}
import com.github.kmruiz.doa.cmsApplication.domain.user.UserService

class ContentManagementSystem(userService: UserService, clientService: ClientService, cardService: CardService) {
  def login(username: String, key: String): Card = {
    val user = userService.findUser(username)
    cardService.createCard(user, key)
  }

  def createClient(origin: String, client: String): Client = {
    val user = userService.findUser(origin)
    clientService.registerClient(user, client)
  }

  def commentClient(token: String, client: String, comment: String): Client = {
    val card = cardService.findCard(token)
    val admin = userService.findAdministrator(card)
    clientService.commentClient(client, admin, comment)
  }
}
