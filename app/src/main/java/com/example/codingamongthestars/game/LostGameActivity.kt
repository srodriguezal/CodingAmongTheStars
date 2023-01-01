package com.example.codingamongthestars.game

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.MainActivity
import com.example.codingamongthestars.R

class LostGameActivity : AppCompatActivity() {
    //private var mediaPlayer: MediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lost_game_screen)

        /*mediaPlayer = MediaPlayer.create(this, R.raw.defeat_song)
        mediaPlayer.start()
        mediaPlayer.isLooping = true*/

        val bundle = intent.extras
        val level = bundle?.getString("level")
        val characterName = bundle?.getString("character")

        val restart: Button = findViewById(R.id.retryButton)
        restart.setOnClickListener {
            val restartIntent = Intent(this, GameActivity::class.java)
            restartIntent.putExtra("level", level)
            restartIntent.putExtra("character", characterName)
            //mediaPlayer.stop()
            startActivity(restartIntent)
        }
        val finishButton: Button = findViewById(R.id.finishLostButton)
        finishButton.setOnClickListener {
            val finishIntent = Intent(this, MainActivity::class.java)
            //mediaPlayer.stop()
            startActivity(finishIntent)
        }
    }
}