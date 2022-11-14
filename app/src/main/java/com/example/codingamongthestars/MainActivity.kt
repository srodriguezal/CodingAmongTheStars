package com.example.codingamongthestars

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.game.GameActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        val startButton: Button = findViewById(R.id.start_button)

        startButton.setOnClickListener {
            startGame()
        }
    }

    private fun startGame() {
        val inicioIntent = Intent(this, GameActivity::class.java)
        startActivity(inicioIntent)
    }
}