package com.example.codingamongthestars.deck

class Deck {

    private val goForwardDeck = Array(12) { "goForward" }
    private val rightDeck = Array(8) { "right" }
    private val leftDeck = Array(8) { "left" }
    private val turnAroundDeck = Array(4) { "turnAround" }
    private val deck = arrayOf(goForwardDeck, rightDeck, leftDeck, turnAroundDeck).flatten()

    fun dealCards(): String {
        return deck.random()
    }
}