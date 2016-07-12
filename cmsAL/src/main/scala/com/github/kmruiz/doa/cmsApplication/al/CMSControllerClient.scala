package com.github.kmruiz.doa.cmsApplication.al

case class LoginRequest(username: String, password: String)
case class CreateClientRequest(origin: String, client: String)
case class CommentClientRequest(token: String, client: String, comment: String)

case class CommentResponse(owner: String, message: String)
case class LoginResponse(owner: String, token: String)
case class CreateClientResponse(username: String, comments: Seq[CommentResponse])
case class CommentClientResponse(username: String, comments: Seq[CommentResponse])

trait CMSControllerClient {
  def login(request: LoginRequest): LoginResponse
  def createClient(createClientRequest: CreateClientRequest): CreateClientResponse
  def commentClient(commentClientResponse: CommentClientRequest): CommentClientResponse
}
