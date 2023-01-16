package com.example.codingamongthestars.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.R
import com.example.codingamongthestars.augmentedReality.AugmentedRealityActivity

class LevelSelectionActivity : AppCompatActivity() {
    private var language: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_selection_screen)

        val bundle = intent.extras
        language = bundle?.getString("language")


        val levelSelectionTextView: TextView = findViewById(R.id.levelSelectionTextView)
        val easyLevelButton: Button = findViewById(R.id.easyLevelButton)
        val mediumLevelButton: Button = findViewById(R.id.mediumLevelButton)
        val hardLevelButton: Button = findViewById(R.id.hardLevelButton)
        val backButton: ImageView = findViewById(R.id.backLevelsButton)

        if (language == "english"){
            levelSelectionTextView.text = this.resources.getString(R.string.level_selection_text_english)
            easyLevelButton.text = this.resources.getString(R.string.easy_level_english)
            mediumLevelButton.text = this.resources.getString(R.string.medium_level_english)
            hardLevelButton.text = this.resources.getString(R.string.hard_level_english)
        }

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
        intentCharacterSelection.putExtra("language", language)
        startActivity(intentCharacterSelection)
    }

    private fun backToARScreen() {
        val backIntent = Intent(this, AugmentedRealityActivity::class.java)
        backIntent.putExtra("language", language)
        startActivity(backIntent)
    }
}