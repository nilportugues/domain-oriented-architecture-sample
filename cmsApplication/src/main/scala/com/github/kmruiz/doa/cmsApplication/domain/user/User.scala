package com.github.kmruiz.doa.cmsApplication.domain.user

import com.github.kmruiz.doa.cmsApplication.domain.card.{Card, CardCreator}
import com.github.kmruiz.doa.cmsApplication.domain.client.{Client, ClientChecker, ClientRegisterer}
import com.github.kmruiz.doa.cmsApplication.domain.comment.OwnedComment

trait AdministratorAccessor {
  def administratorId(): String
}

trait User extends ClientRegisterer with CardCreator
trait AdministratorUser extends User with ClientChecker

case class Administrator(username: String, key: String) extends AdministratorUser {
  override def registerClient(id: String): Client = Client.webApp(id)
  override def createCard(password: String): Card = password == key match {
    case true => Card.forUser(username)
    case _ => throw new IllegalAccessException()
  }

  override def commentOnClient(client: Client, message: String): Client = client.addComment(OwnedComment(username, message))
}

case class BatchProcess(origin: String) extends User {
  override def registerClient(id: String): Client = Client.webApp(id)
  override def createCard(password: String): Card = Card.forUser(origin)
}

object User {
  def administrator(username: String, key: String): AdministratorUser = Administrator(username, key)
  def batch(origin: String): User = BatchProcess(origin)
}