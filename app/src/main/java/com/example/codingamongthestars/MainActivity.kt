package com.example.codingamongthestars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.codingamongthestars.deck.Deck

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)

        // Esto irá en la clase que llame a game_screen
        val deckRollButton: Button = findViewById(R.id.deck_button)

        deckRollButton.setOnClickListener {
            dealCards()
        }


    }

    private fun dealCards() {
        val deck = Deck()
        val cardImage: ImageView = findViewById(R.id.imgViewCard)

        when (deck.dealCards()) {
            "goForward" -> cardImage.setImageResource(R.drawable.card_go_forward)
            "right" -> cardImage.setImageResource(R.drawable.card_right)
            "left" -> cardImage.setImageResource(R.drawable.card_left)
            "turnAround" -> cardImage.setImageResource(R.drawable.card_turn_around)

        }

    }


}