package com.example.oxofrenzy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class splash_screen : AppCompatActivity() {

    private lateinit var playerVsPlayerToggle: RadioButton
    private lateinit var playerVsComputerToggle: RadioButton
    private lateinit var chooseXToggle: RadioButton
    private lateinit var chooseOToggle: RadioButton
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        playerVsPlayerToggle = findViewById(R.id.playerVsPlayerToggle)
        playerVsComputerToggle = findViewById(R.id.playerVsComputerToggle)
        chooseXToggle = findViewById(R.id.chooseXToggle)
        chooseOToggle = findViewById(R.id.chooseOToggle)
        startButton = findViewById(R.id.start)

        startButton.setOnClickListener {
            val isVsComputer = playerVsComputerToggle.isChecked
            val player1Symbol = if (chooseXToggle.isChecked) "X" else "O"

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("IS_VS_COMPUTER", isVsComputer)
            intent.putExtra("PLAYER1_SYMBOL", player1Symbol)
            startActivity(intent)
        }
    }
}