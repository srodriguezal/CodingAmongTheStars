package com.example.codingamongthestars.deck

class Deck {
    private var deck: MutableList<String> = mutableListOf()

    init {
        deck.addAll(Array(12) { "goForward" })
        deck.addAll(Array(8) { "right" })
        deck.addAll(Array(8) { "left" })
        deck.addAll(Array(4) { "turnAround" })
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
