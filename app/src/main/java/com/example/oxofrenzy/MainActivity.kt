package com.example.oxofrenzy

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var statusTextView: TextView
    private lateinit var resetButton: Button

    private var isPlayer1Turn = true
    private var gameBoard = Array(3) { arrayOfNulls<String>(3) }
    private var isVsComputer = false
    private var player1Symbol = "X"
    private var player2Symbol = "O"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        statusTextView = findViewById(R.id.statusTextView)
        resetButton = findViewById(R.id.resetButton)

        // Getdata from splash screen activity
        isVsComputer = intent.getBooleanExtra("IS_VS_COMPUTER", false)
        player1Symbol = intent.getStringExtra("PLAYER1_SYMBOL") ?: "X"
        player2Symbol = if (player1Symbol == "X") "O" else "X"

        initializeBoard()
        resetGame()

        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun initializeBoard() {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener {
                onButtonClick(button, i / 3, i % 3)
            }
        }
    }

    private fun onButtonClick(button: Button, row: Int, col: Int) {
        if (button.text.isEmpty() && gameBoard[row][col] == null) {
            gameBoard[row][col] = if (isPlayer1Turn) player1Symbol else player2Symbol
            button.text = gameBoard[row][col]

            if (checkForWin()) {
                statusTextView.text = "${gameBoard[row][col]} Wins!"
                disableButtons()
            } else if (isBoardFull()) {
                statusTextView.text = "It's a Draw!"
            } else {
                isPlayer1Turn = !isPlayer1Turn
                updateStatusText()
                if (isVsComputer && !isPlayer1Turn) {
                    computerMove()
                }
            }
        }
    }

    private fun computerMove() {
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0..2) {
            for (j in 0..2) {
                if (gameBoard[i][j] == null) {
                    emptyCells.add(Pair(i, j))
                }
            }
        }
        if (emptyCells.isNotEmpty()) {
            val randomIndex = (0 until emptyCells.size).random()
            val (row, col) = emptyCells[randomIndex]
            val buttonIndex = row * 3 + col
            val button = gridLayout.getChildAt(buttonIndex) as Button
            onButtonClick(button, row, col)
        }
    }

    private fun checkForWin(): Boolean {
        for (i in 0..2) {
            if (checkLine(gameBoard[i][0], gameBoard[i][1], gameBoard[i][2]) ||
                checkLine(gameBoard[0][i], gameBoard[1][i], gameBoard[2][i])) {
                return true
            }
        }
        return checkLine(gameBoard[0][0], gameBoard[1][1], gameBoard[2][2]) ||
                checkLine(gameBoard[0][2], gameBoard[1][1], gameBoard[2][0])
    }

    private fun checkLine(a: String?, b: String?, c: String?): Boolean {
        return a != null && a == b && a == c
    }

    private fun isBoardFull(): Boolean {
        return gameBoard.all { row -> row.all { cell -> cell != null } }
    }

    private fun resetGame() {
        gameBoard = Array(3) { arrayOfNulls<String>(3) }
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
            button.isEnabled = true
        }
        isPlayer1Turn = true
        updateStatusText()
    }

    private fun disableButtons() {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.isEnabled = false
        }
    }

    private fun updateStatusText() {
        val currentPlayerSymbol = if (isPlayer1Turn) player1Symbol else player2Symbol
        val playerText = if (isVsComputer && !isPlayer1Turn) "Computer" else "Player ${if (isPlayer1Turn) 1 else 2}"
        statusTextView.text = "$playerText's Turn ($currentPlayerSymbol)"
    }
}