package com.example.codingamongthestars.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.R
import com.example.codingamongthestars.augmentedReality.AugmentedRealityActivity

class LevelSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_selection_screen)

        val easyLevelButton: Button = findViewById(R.id.easyLevelButton)
        val mediumLevelButton: Button = findViewById(R.id.mediumLevelButton)
        val hardLevelButton: Button = findViewById(R.id.hardLevelButton)
        val backButton: ImageView = findViewById(R.id.backLevelsButton)

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
            backToARScreen()
        }
    }

    private fun selectCharacter(level : String){
        val intentCharacterSelection = Intent(this, CharacterSelectionActivity::class.java)
        intentCharacterSelection.putExtra("level", level)
        startActivity(intentCharacterSelection)
    }

    private fun backToARScreen() {
        val backIntent = Intent(this, AugmentedRealityActivity::class.java)
        startActivity(backIntent)
    }
}