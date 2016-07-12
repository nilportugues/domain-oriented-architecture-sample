package com.github.kmruiz.doa.cmsApplication.domain.user

case class UserService(repository: UserRepository) {
  def findUser(name: String): User = repository.userByName(name)
  def findAdministrator(accessor: AdministratorAccessor): AdministratorUser = repository.administratorByName(accessor.administratorId())
}
