package com.github.kmruiz.doa.cmsApplication.infrastructure.client

import com.github.kmruiz.doa.cmsApplication.domain.client.{Client, ClientRepository, WebApplicationClient}

class AwesomeClientRepository(private var clientMap: Map[String, Client] = Map()) extends ClientRepository {
  override def save(client: Client): Client = client match {
    case WebApplicationClient(id) =>
      clientMap = clientMap + (id -> client)
      client
    case _ => client
  }
  override def byId(id: String): Client = clientMap(id)
}
