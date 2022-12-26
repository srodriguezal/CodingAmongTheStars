package com.example.codingamongthestars.game

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.R

class CharacterSelectionActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_selection_screen)

        val kotlinButton: Button = findViewById(R.id.kotlin_button)
        val rubyButton: Button = findViewById(R.id.ruby_button)

        kotlinButton.setOnClickListener {
            startGame()
        }

        rubyButton.setOnClickListener {
            startGame()
        }
    }

    private fun startGame(){
        val inicioIntent = Intent(this, GameActivity::class.java)
        startActivity(inicioIntent)
    }
}