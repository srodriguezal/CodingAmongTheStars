package com.example.codingamongthestars.deck

class Deck {

    private val goForwardDeck = Array(12) { "goForward" }
    private val rightDeck = Array(8) { "right" }
    private val leftDeck = Array(8) { "left" }
    private val turnAroundDeck = Array(4) { "turnAround" }
    private val deck = arrayOf(goForwardDeck, rightDeck, leftDeck, turnAroundDeck).flatten()

    fun dealCard(): String {
        if (deck.isNotEmpty()){
            val card = deck.random()
            removeCardFromDeck(card)
            return card

        } else
            return "None"

    }

    fun removeCardFromDeck(card: String): Array<String> {
        return deck
            .filter { it != card }
            .toTypedArray()
    }

}
