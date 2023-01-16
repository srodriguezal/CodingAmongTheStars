package com.example.codingamongthestars

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.augmentedReality.AugmentedRealityActivity


class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        mediaPlayer = MediaPlayer.create(this, R.raw.intro_song)
        mediaPlayer.start()
        mediaPlayer.isLooping = true

        val sound: ImageView = findViewById(R.id.sound_main_button)
        var soundOn = true
        sound.setOnClickListener {
            soundOn = if (soundOn){
                mediaPlayer.pause()
                sound.setImageResource(R.drawable.sound_off)
                false
            } else{
                mediaPlayer.start()
                sound.setImageResource(R.drawable.sound_on)
                true

            }
        }
        var language = "spanish"
        val languageButton: ImageView = findViewById(R.id.language_button)

        val startButton: Button = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            startGame(language)
        }

        languageButton.setOnClickListener{
            if (language == "spanish"){
                language = "english"
                languageButton.setImageResource(R.drawable.spanish)
                startButton.text = this.resources.getString(R.string.start_english)

            } else {
                language = "spanish"
                languageButton.setImageResource(R.drawable.english)
                startButton.text = this.resources.getString(R.string.start_spanish)
            }
        }
    }

    private fun startGame(language: String) {
        val startIntent = Intent(this, AugmentedRealityActivity::class.java)
        mediaPlayer.stop()
        startIntent.putExtra("language", language)
        startActivity(startIntent)
    }
}