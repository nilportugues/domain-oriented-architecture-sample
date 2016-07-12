package com.github.kmruiz.doa.cmsApplication.domain.card

case class CardService(repository: CardRepository) {
  def createCard(creator: CardCreator, key: String): Card = repository.save(creator.createCard(key))
  def findCard(token: String): Card = repository.cardByToken(token)
}
