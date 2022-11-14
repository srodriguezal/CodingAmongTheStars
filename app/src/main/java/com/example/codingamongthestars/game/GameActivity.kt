package com.example.codingamongthestars.game

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.R
import com.example.codingamongthestars.deck.Deck

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)

        val deck = Deck()
        val newCard1Image: ImageView = findViewById(R.id.imgViewNewCard1)
        val newCard2Image: ImageView = findViewById(R.id.imgViewNewCard2)
        val card1Image: ImageView = findViewById(R.id.imgViewCard1)
        val card2Image: ImageView = findViewById(R.id.imgViewCard2)
        val card3Image: ImageView = findViewById(R.id.imgViewCard3)
        val card4Image: ImageView = findViewById(R.id.imgViewCard4)

        setPlayerDeck(deck, card1Image, card2Image, card3Image, card4Image)

        val deckRollButton: Button = findViewById(R.id.deck_button)

        deckRollButton.setOnClickListener {
            drawCards(deck, newCard1Image, newCard2Image)
        }


    }

    private fun setPlayerDeck(
        deck: Deck, card1: ImageView, card2: ImageView, card3: ImageView,
        card4: ImageView
    ) {
        dealCard(deck, card1)
        dealCard(deck, card2)
        dealCard(deck, card3)
        dealCard(deck, card4)


    }

    private fun setImageCard(card: String, cardImage: ImageView) {
        when (card) {
            "goForward" -> cardImage.setImageResource(R.drawable.card_go_forward)
            "right" -> cardImage.setImageResource(R.drawable.card_right)
            "left" -> cardImage.setImageResource(R.drawable.card_left)
            "turnAround" -> cardImage.setImageResource(R.drawable.card_turn_around)
            "None" -> cardImage.setImageResource(R.drawable.card_back)

        }

    }

    private fun dealCard(deck: Deck, cardImage: ImageView) {
        setImageCard(deck.dealCard(), cardImage)


    }

    private fun drawCards(deck: Deck, card1: ImageView, card2: ImageView) {
        dealCard(deck, card1)
        dealCard(deck, card2)

    }


}
