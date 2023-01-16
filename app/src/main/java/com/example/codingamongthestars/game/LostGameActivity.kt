package com.example.codingamongthestars.game

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.MainActivity
import com.example.codingamongthestars.R

class LostGameActivity : AppCompatActivity() {
    //private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var language: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lost_game_screen)

        /*mediaPlayer = MediaPlayer.create(this, R.raw.defeat_song)
        mediaPlayer.start()
        mediaPlayer.isLooping = true*/

        val bundle = intent.extras
        val level = bundle?.getString("level")
        val characterName = bundle?.getString("character")
        language = bundle?.getString("language")

        val lostGameTextView1: TextView = findViewById(R.id.lostScreenTextView1)
        val lostGameTextView2: TextView = findViewById(R.id.lostScreenTextView2)

        val restart: Button = findViewById(R.id.retryButton)
        restart.setOnClickListener {
            val restartIntent = Intent(this, GameActivity::class.java)
            restartIntent.putExtra("level", level)
            restartIntent.putExtra("character", characterName)
            restartIntent.putExtra("language", language)
            //mediaPlayer.stop()
            startActivity(restartIntent)
        }
        val finishButton: Button = findViewById(R.id.finishLostButton)
        finishButton.setOnClickListener {
            val finishIntent = Intent(this, MainActivity::class.java)
            //mediaPlayer.stop()
            finishIntent.putExtra("language", language)
            startActivity(finishIntent)
        }

        if (language == "english"){
            lostGameTextView1.text = this.resources.getString(R.string.lost_screen_text_english_1)
            lostGameTextView2.text = this.resources.getString(R.string.lost_screen_text_english_2)
            restart.text= this.resources.getString(R.string.retry_english)
            finishButton.text = this.resources.getString(R.string.finish_english)
        }
    }
}