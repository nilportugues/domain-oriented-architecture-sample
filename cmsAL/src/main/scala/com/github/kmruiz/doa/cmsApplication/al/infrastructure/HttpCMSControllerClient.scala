package com.github.kmruiz.doa.cmsApplication.al.infrastructure

import java.io.InputStream

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kmruiz.doa.cmsApplication.al.{CommentClientResponse, LoginResponse, _}
import org.apache.http.client.methods.{HttpPost, HttpPut}
import org.apache.http.entity.{ContentType, StringEntity}
import org.apache.http.impl.client.HttpClients

import scala.io.Source

case class HttpCMSControllerClient(host: String, objectMapper: ObjectMapper) extends CMSControllerClient {
  override def login(request: LoginRequest): LoginResponse = {
    val client = HttpClients.createDefault()
    val put = new HttpPut(s"$host/cms/login")
    put.setEntity(new StringEntity(objectMapper.writeValueAsString(request), ContentType.APPLICATION_JSON))
    val response = client.execute(put)
    val data = isToString(response.getEntity.getContent)
    client.close()
    objectMapper.readValue(data, classOf[LoginResponse])
  }

  override def commentClient(request: CommentClientRequest): CommentClientResponse = {
    val client = HttpClients.createDefault()
    val put = new HttpPut(s"$host/cms/comment")
    put.setEntity(new StringEntity(objectMapper.writeValueAsString(request), ContentType.APPLICATION_JSON))
    val response = client.execute(put)
    val data = isToString(response.getEntity.getContent)
    client.close()
    objectMapper.readValue(data, classOf[CommentClientResponse])
  }

  override def createClient(request: CreateClientRequest): CreateClientResponse = {
    val client = HttpClients.createDefault()
    val post = new HttpPost(s"$host/cms/client")
    post.setEntity(new StringEntity(objectMapper.writeValueAsString(request), ContentType.APPLICATION_JSON))
    val response = client.execute(post)
    val data = isToString(response.getEntity.getContent)
    client.close()
    objectMapper.readValue(data, classOf[CreateClientResponse])
  }

  private def isToString(is: InputStream): String = Source.fromInputStream(is).getLines().mkString("\n")
}
