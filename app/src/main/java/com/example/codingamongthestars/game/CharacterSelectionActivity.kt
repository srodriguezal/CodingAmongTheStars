package com.example.codingamongthestars.game

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.R

class CharacterSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_selection_screen)

        val bundle = intent.extras
        val level = bundle?.getString("level")

        val kotlinButton: ImageView = findViewById(R.id.kotlinButton)
        val rubyButton: ImageView = findViewById(R.id.rubyButton)
        val backButton: ImageView = findViewById(R.id.backCharactersButton)

        kotlinButton.setOnClickListener {
            startGame("kotlin", level)
        }

        rubyButton.setOnClickListener {
            startGame("ruby", level)
        }
        backButton.setOnClickListener {
            backToLevelSelection()
        }
    }

    private fun startGame(character: String, level: String?){
        val startGameIntent = Intent(this, GameActivity::class.java)
        startGameIntent.putExtra("level", level)
        startGameIntent.putExtra("character", character)
        startActivity(startGameIntent)
    }

    private fun backToLevelSelection() {
        val backIntent = Intent(this, LevelSelectionActivity::class.java)
        startActivity(backIntent)
    }
}