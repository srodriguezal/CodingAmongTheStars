package com.example.codingamongthestars.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.MainActivity
import com.example.codingamongthestars.R

class LevelSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_selection_screen)

        val easyLevelButton: Button = findViewById(R.id.easyLevelButton)
        val mediumLevelButton: Button = findViewById(R.id.mediumLevelButton)
        val hardLevelButton: Button = findViewById(R.id.hardLevelButton)
        val backButton: ImageView = findViewById(R.id.back_levels_button)

        easyLevelButton.setOnClickListener {
            selectCharacter("easy")
        }

        mediumLevelButton.setOnClickListener {
            selectCharacter("medium")
        }

        hardLevelButton.setOnClickListener {
            selectCharacter("hard")
        }
        backButton.setOnClickListener {
            backToMainScreen()
        }
    }

    private fun selectCharacter(level : String){
        val intentCharacterSelection = Intent(this, CharacterSelectionActivity::class.java)
        intentCharacterSelection.putExtra("level", level)
        startActivity(intentCharacterSelection)
    }

    private fun backToMainScreen() {
        val backIntent = Intent(this, MainActivity::class.java)
        startActivity(backIntent)
    }
}