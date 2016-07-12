package com.github.kmruiz.doa.cmsApplication.al.infrastructure

import java.io.InputStream

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kmruiz.doa.cmsApplication.al.{CommentClientResponse, LoginResponse, _}
import org.apache.http.client.methods.HttpPut
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients

import scala.io.Source

class HttpCMSControllerClient(host: String, objectMapper: ObjectMapper) extends CMSControllerClient {
  override def login(request: LoginRequest): LoginResponse = {
    val client = HttpClients.createDefault()
    val put = new HttpPut(s"$host/cms/login")
    put.setEntity(new StringEntity(objectMapper.writeValueAsString(request)))
    val response = client.execute(put)
    val data = isToString(response.getEntity.getContent)
    client.close()
    objectMapper.readValue(data, classOf[LoginResponse])
  }

  override def commentClient(request: CommentClientRequest): CommentClientResponse = {
    val client = HttpClients.createDefault()
    val put = new HttpPut(s"$host/cms/comment")
    put.setEntity(new StringEntity(objectMapper.writeValueAsString(request)))
    val response = client.execute(put)
    val data = isToString(response.getEntity.getContent)
    client.close()
    objectMapper.readValue(data, classOf[CommentClientResponse])
  }

  override def createClient(request: CreateClientRequest): CreateClientResponse = {
    val client = HttpClients.createDefault()
    val put = new HttpPut(s"$host/cms/client")
    put.setEntity(new StringEntity(objectMapper.writeValueAsString(request)))
    val response = client.execute(put)
    val data = isToString(response.getEntity.getContent)
    client.close()
    objectMapper.readValue(data, classOf[CreateClientResponse])
  }

  private def isToString(is: InputStream): String = Source.fromInputStream(is).getLines().mkString("\n")
}
