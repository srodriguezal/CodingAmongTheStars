package com.example.codingamongthestars.augmentedReality

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.MainActivity
import com.example.codingamongthestars.R
import com.example.codingamongthestars.game.LevelSelectionActivity

class AugmentedRealityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ar_selection_screen)

        val yesButton: Button = findViewById(R.id.yesARButton)
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
            packageManager.getLaunchIntentForPackage("com.sararodriguezalarcon.codingamonthestarsar")
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
            setTitle(R.string.ar_alert)
            setIcon(R.drawable.alert)
            setPositiveButton(R.string.ar_alert_button, DialogInterface.OnClickListener(function = positiveButtonClick))
            setCancelable(false)
            show()
        }


    }

    private fun selectLevel() {
        val intentLevelSelection = Intent(this, LevelSelectionActivity::class.java)
        startActivity(intentLevelSelection)
    }

    private fun backToMainScreen() {
        val backIntent = Intent(this, MainActivity::class.java)
        startActivity(backIntent)
    }
}