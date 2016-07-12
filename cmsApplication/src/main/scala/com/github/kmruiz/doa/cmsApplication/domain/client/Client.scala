package com.github.kmruiz.doa.cmsApplication.domain.client

import com.github.kmruiz.doa.cmsApplication.domain.comment.Comment

trait Client {
  def addComment(comment: Comment): Client
}

trait ClientChecker {
  def commentOnClient(client: Client, message: String): Client
}

trait ClientRegisterer {
  def registerClient(username: String): Client
}

case class WebApplicationClient(username: String, comments: Seq[Comment]) extends Client {
  override def addComment(comment: Comment) = copy(comments = comments :+ comment)
}

object Client {
  def webApp(name: String): Client = WebApplicationClient(name, Seq.empty)
}