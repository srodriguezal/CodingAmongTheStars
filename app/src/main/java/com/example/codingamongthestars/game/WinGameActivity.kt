package com.example.codingamongthestars.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.MainActivity
import com.example.codingamongthestars.R

class WinGameActivity : AppCompatActivity() {
    private var language: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.win_game_screen)

        val bundle = intent.extras
        language = bundle?.getString("language")

        val winTextView: TextView = findViewById(R.id.winScreenTextView)

        val chooseLevelsButton: Button = findViewById(R.id.chooseLevelButton)
        chooseLevelsButton.setOnClickListener {
            val levelIntent = Intent(this, LevelSelectionActivity::class.java)
            levelIntent.putExtra("language", language)
            startActivity(levelIntent)
        }
        val finishButton: Button = findViewById(R.id.finishWinButton)
        finishButton.setOnClickListener {
            val finishIntent = Intent(this, MainActivity::class.java)
            finishIntent.putExtra("language", language)
            startActivity(finishIntent)
        }

        if (language == "english"){
            winTextView.text = this.resources.getString(R.string.win_screen_text_english)
            chooseLevelsButton.text = this.resources.getString(R.string.yes_english)
        }
    }
}