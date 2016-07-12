package com.github.kmruiz.doa.cmsApplication.domain.card

trait CardRepository {
  def save(card: Card): Card
  def cardByToken(token: String): Card
}
