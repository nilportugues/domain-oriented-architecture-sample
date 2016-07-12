package com.github.kmruiz.doa.cmsApplication.domain.comment

trait Comment

case class OwnedComment(owner: String, message: String) extends Comment
