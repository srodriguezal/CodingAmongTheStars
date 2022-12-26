package com.example.codingamongthestars.game


import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.R
import com.example.codingamongthestars.deck.Deck

class GameActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)

        val bundle = intent.extras
        val level = bundle?.getString("level")
        val character = bundle?.getString("character")

        // El código a continuación es solo para probar que se estén pasando los extras
        val characterImage: ImageView = findViewById(R.id.imageView_character)
        val currentLevel: TextView = findViewById(R.id.textView_level)

        currentLevel.text = level

        when (character){
            "kotlin" -> characterImage.setImageResource(R.drawable.kotlin_200x200)
            "ruby" -> characterImage.setImageResource(R.drawable.ruby_200x200)
        }
        ///

        val backButton : Button = findViewById(R.id.back_game_button)

        backButton.setOnClickListener {
            backToSelectCharacter(level)
        }

        val deck = Deck()
        val newCard1Image: ImageView = findViewById(R.id.imgViewNewCard1)
        val newCard2Image: ImageView = findViewById(R.id.imgViewNewCard2)
        val card1Image: ImageView = findViewById(R.id.imgViewCard1)
        val card2Image: ImageView = findViewById(R.id.imgViewCard2)
        val card3Image: ImageView = findViewById(R.id.imgViewCard3)
        val card4Image: ImageView = findViewById(R.id.imgViewCard4)

        card1Image.apply {
            setOnLongClickListener { v ->
                val item = ClipData.Item(v.tag as? CharSequence)
                val dragData = ClipData(
                    v.tag as? CharSequence,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    item
                )

                val myShadow = MyDragShadowBuilder(this)

                v.startDragAndDrop(dragData, myShadow, null, 0)

                true
            }
        }

            setPlayerDeck(deck, card1Image, card2Image, card3Image, card4Image)

            val deckRollButton: Button = findViewById(R.id.deck_button)

            deckRollButton.setOnClickListener {
                newCard1Image.visibility = View.VISIBLE
                newCard2Image.visibility = View.VISIBLE
                drawCards(deck, newCard1Image, newCard2Image)
            }


        }

    private fun backToSelectCharacter(level : String?) {
        val backIntent = Intent(this, CharacterSelectionActivity::class.java)
        backIntent.putExtra("level", level)
        startActivity(backIntent)
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
    private class MyDragShadowBuilder(v: View) : View.DragShadowBuilder(v) {

        private val shadow = ColorDrawable(Color.LTGRAY)
        override fun onProvideShadowMetrics(size: Point, touch: Point) {
            val width: Int = view.width / 2
            val height: Int = view.height / 2
            shadow.setBounds(0, 0, width, height)
            size.set(width, height)
            touch.set(width / 2, height / 2)
        }
        override fun onDrawShadow(canvas: Canvas) {
            shadow.draw(canvas)
        }
    }
