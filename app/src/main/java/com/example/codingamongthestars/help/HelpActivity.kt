package com.example.codingamongthestars.help

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import com.example.codingamongthestars.MainActivity
import com.example.codingamongthestars.R
import com.example.codingamongthestars.game.GameActivity

class HelpActivity : AppCompatActivity() {
    private var language: String? = null
    private var screen: String? = null
    private var level: String? = null
    private var characterName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_screen)

        val bundle = intent.extras
        language = bundle?.getString("language")
        screen = bundle?.getString("screen")
        level = bundle?.getString("level")
        characterName = bundle?.getString("character")

        val helpTitleTextView: TextView = findViewById(R.id.helpTitleTextView)
        val helpTextView: TextView = findViewById(R.id.helpTextView)
        if (language == "english"){
            helpTitleTextView.text = this.resources.getString(R.string.help_screen_title_english)
            helpTextView.text = this.resources.getString(R.string.help_screen_text_english)
        }

        val backButton: ImageView = findViewById(R.id.backHelpButton)

        backButton.setOnClickListener {
            backToPrevious()
        }
    }

    private fun backToPrevious(){
        if (screen == "main"){
            val backIntent = Intent(this, MainActivity::class.java)
            backIntent.putExtra("language", language)
            startActivity(backIntent)

        } else {
            val backIntent = Intent(this, GameActivity::class.java)
            backIntent.putExtra("language", language)
            backIntent.putExtra("level", level)
            backIntent.putExtra("character", characterName)
            startActivity(backIntent)
        }
    }
}