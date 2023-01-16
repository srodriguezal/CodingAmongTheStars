package com.example.codingamongthestars.augmentedReality

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.MainActivity
import com.example.codingamongthestars.R
import com.example.codingamongthestars.game.LevelSelectionActivity

class AugmentedRealityActivity : AppCompatActivity() {
    private var language: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ar_selection_screen)

        val bundle = intent.extras
        language = bundle?.getString("language")

        val arTextView1: TextView = findViewById(R.id.arSelectionTextView1)
        val arTextView2: TextView = findViewById(R.id.arSelectionTextView2)
        if (language == "english"){
            arTextView1.text = this.resources.getString(R.string.ar_selection_text_english_1)
            arTextView2.text = this.resources.getString(R.string.ar_selection_text_english_2)
        }

        val yesButton: Button = findViewById(R.id.yesARButton)
        if (language == "english"){
            yesButton.text = this.resources.getString(R.string.yes_english)
        }

        val noButton: Button = findViewById(R.id.noARButton)
        val backButton: ImageView = findViewById(R.id.backARButton)

        yesButton.setOnClickListener {
            startAR()
        }

        noButton.setOnClickListener {
            selectLevel()
        }
        backButton.setOnClickListener {
            backToMainScreen()
        }
    }

    private fun startAR() {
        val intentStartAR =
            packageManager.getLaunchIntentForPackage("com.codingAmongTheStarsAr")
        if (intentStartAR != null) {
            startActivity(intentStartAR)
        } else {
            createAlertPopUp()
        }

    }

    private fun createAlertPopUp(){
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogCustom))
        val positiveButtonClick = { _: DialogInterface, _: Int ->
        }

        with(builder)
        {
            if (language == "spanish"){
                setTitle(R.string.ar_alert_spanish)
                setPositiveButton(R.string.ar_alert_button_spanish, DialogInterface.OnClickListener(function = positiveButtonClick))

            }
            else {
                setTitle(R.string.ar_alert_english)
                setPositiveButton(R.string.ar_alert_button_english, DialogInterface.OnClickListener(function = positiveButtonClick))

            }

            setIcon(R.drawable.alert)
            setCancelable(false)
            show()
        }


    }

    private fun selectLevel() {
        val intentLevelSelection = Intent(this, LevelSelectionActivity::class.java)
        intentLevelSelection.putExtra("language", language)
        startActivity(intentLevelSelection)
    }

    private fun backToMainScreen() {
        val backIntent = Intent(this, MainActivity::class.java)
        startActivity(backIntent)
    }
}