package com.github.kmruiz.doa.cmsApplication.infrastructure.user

import java.util.NoSuchElementException

import com.github.kmruiz.doa.cmsApplication.domain.user.{AdministratorUser, User, UserRepository}

class AwesomeUserRepository(private var userMap: Map[String, User] = Map()) extends UserRepository {
  override def userByName(name: String): User = userMap(name)
  override def administratorByName(name: String): AdministratorUser = userMap.get(name) match {
    case Some(admin: AdministratorUser) => admin
    case _ => throw new NoSuchElementException(name)
  }
}
