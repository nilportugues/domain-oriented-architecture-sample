package com.github.kmruiz.doa.cmsApplication.domain.card

import java.util.UUID

import com.github.kmruiz.doa.cmsApplication.domain.user.AdministratorAccessor

trait Card extends AdministratorAccessor

trait CardCreator {
  def createCard(password: String): Card
}

case class UserCard(owner: String, token: String) extends Card {
  def this(owner: String) = this(owner, UUID.randomUUID().toString)
  override def administratorId(): String = owner
}

object Card {
  def forUser(owner: String): Card = new UserCard(owner)
}