package com.example.codingamongthestars.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.MainActivity
import com.example.codingamongthestars.R

class WinGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.win_game_screen)
        val chooseLevelsButton: Button = findViewById(R.id.chooseLevelButton)
        chooseLevelsButton.setOnClickListener {
            val levelIntent = Intent(this, LevelSelectionActivity::class.java)
            startActivity(levelIntent)
        }
        val finishButton: Button = findViewById(R.id.finishWinButton)
        finishButton.setOnClickListener {
            val finishIntent = Intent(this, MainActivity::class.java)
            startActivity(finishIntent)
        }
    }
}