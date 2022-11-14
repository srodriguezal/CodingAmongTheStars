package com.example.codingamongthestars.deck

class Deck {

    private val goForwardDeck = Array(12) { "goForward" }
    private val rightDeck = Array(8) { "right" }
    private val leftDeck = Array(8) { "left" }
    private val turnAroundDeck = Array(4) { "turnAround" }
    private var deck: MutableList<String> = mutableListOf()

    init {
        deck.addAll(goForwardDeck)
        deck.addAll(rightDeck)
        deck.addAll(leftDeck)
        deck.addAll(turnAroundDeck)
    }


    fun dealCard(): String {
        return if (deck.isNotEmpty()){
            val card = deck.random()
            deck.remove(card)
            card

        } else
            "None"

    }

}
