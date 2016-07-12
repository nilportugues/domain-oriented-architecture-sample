package com.github.kmruiz.doa.cmsApplication.domain.client

class ClientService(repository: ClientRepository) {
  def registerClient(registerer: ClientRegisterer, username: String): Client = {
    repository.save(registerer.registerClient(username))
  }

  def commentClient(clientId: String, checker: ClientChecker, message: String): Client = {
    val client = checker.commentOnClient(repository.byId(clientId), message)
    repository.save(client)
  }
}