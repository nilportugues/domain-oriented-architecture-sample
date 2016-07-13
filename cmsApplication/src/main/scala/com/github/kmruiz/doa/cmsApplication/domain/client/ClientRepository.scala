package com.github.kmruiz.doa.cmsApplication.domain.client

trait ClientRepository {
  def createClient(client: Client): Client
  def save(client: Client): Client
  def byId(id: String): Client
}
