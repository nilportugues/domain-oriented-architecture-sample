package com.github.kmruiz.doa.cmsApplication.domain.user

trait UserRepository {
  def userByName(name: String): User
  def administratorByName(name: String): AdministratorUser
}
