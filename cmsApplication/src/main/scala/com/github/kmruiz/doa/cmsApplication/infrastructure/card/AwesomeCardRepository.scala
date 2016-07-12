package com.github.kmruiz.doa.cmsApplication.infrastructure.card

import com.github.kmruiz.doa.cmsApplication.domain.card.{Card, CardRepository, UserCard}

class AwesomeCardRepository(private var cardMap: Map[String, Card] = Map()) extends CardRepository {
  override def save(card: Card): Card = card match {
    case UserCard(_, token) =>
      cardMap = cardMap + (token -> card)
      card
    case _ =>
      card
  }

  override def cardByToken(token: String): Card = cardMap(token)
}
