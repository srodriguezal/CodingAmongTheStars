package com.example.codingamongthestars.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.R

class LevelSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_selection_screen)

        val easyLevelButton: Button = findViewById(R.id.easyLevelButton)
        val mediumLevelButton: Button = findViewById(R.id.mediumLevelButton)
        val hardLevelButton: Button = findViewById(R.id.hardLevelButton)

        easyLevelButton.setOnClickListener {
            selectCharacter()
        }

        mediumLevelButton.setOnClickListener {
            selectCharacter()
        }

        hardLevelButton.setOnClickListener {
            selectCharacter()
        }
    }

    private fun selectCharacter(){
        val inicioIntent = Intent(this, CharacterSelectionActivity::class.java)
        startActivity(inicioIntent)
    }
}