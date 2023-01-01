package com.example.codingamongthestars

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.game.LevelSelectionActivity


class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        mediaPlayer = MediaPlayer.create(this, R.raw.intro_song)
        mediaPlayer.start()
        mediaPlayer.isLooping = true

        val startButton: Button = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            startGame()
        }
    }

    private fun startGame() {
        val startIntent = Intent(this, LevelSelectionActivity::class.java)
        mediaPlayer.stop()
        startActivity(startIntent)
    }
}