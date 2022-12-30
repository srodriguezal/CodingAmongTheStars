package com.example.codingamongthestars.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.example.codingamongthestars.R
import com.example.codingamongthestars.deck.Deck


var matrix_board : Array<Array<Cell?>> = emptyArray()

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)

        val bundle = intent.extras
        val level = bundle?.getString("level")
        val character = bundle?.getString("character")

        val backButton: ImageView = findViewById(R.id.backGameButton)

        backButton.setOnClickListener {
            backToSelectCharacter(level)
        }

        val board: TableLayout = findViewById(R.id.gameTableLayout)
        board.removeAllViews()
        setBoard(level, character, board)

        val deck = Deck()
        val newCardImage : ImageView = findViewById(R.id.imgViewNewCard)
        val card1Image: ImageView = findViewById(R.id.imgViewCard1)
        val card2Image: ImageView = findViewById(R.id.imgViewCard2)
        val card3Image: ImageView = findViewById(R.id.imgViewCard3)
        val card4Image: ImageView = findViewById(R.id.imgViewCard4)


        setPlayerDeck(deck, card1Image, card2Image, card3Image, card4Image)

        val deckRollButton: ImageView = findViewById(R.id.deckButton)

        deckRollButton.setOnClickListener {
            newCardImage.visibility = View.VISIBLE
            drawCards(deck, newCardImage)
        }


    }

    private fun backToSelectCharacter(level: String?) {
        val backIntent = Intent(this, CharacterSelectionActivity::class.java)
        backIntent.putExtra("level", level)
        startActivity(backIntent)
    }

    private fun setBoard(level: String?, character: String?, board: TableLayout) {
        when (level) {
            "easy" -> {
                createMatrix(character,4, 4, 1, 0)
                createVisualBoard(board, 4, level)

            }
            "medium" -> {
                createMatrix(character,6, 6, 2, 1)
                createVisualBoard(board, 6, level)

            }
            "hard" -> {
                createMatrix(character,8, 18, 4, 2)
                createVisualBoard(board, 8, level)

            }
        }
    }
    private fun createMatrix(character: String?, numCells : Int, numBlocks: Int, numBugs: Int, numCPUs: Int){
        val matrix = Array(numCells){Array<Cell?>(numCells){null} }
        for (i in (0 until numCells)){
            for (j in(0 until  numCells)){
                if ((i == (numCells/2)-1) && (j == (numCells/2)-1)){
                    val cell = Cell("planet01")
                    matrix[i][j] = cell

                } else if ((i == (numCells/2)-1) &&  (j == (numCells/2))){
                    val cell = Cell("planet02")
                    matrix[i][j] = cell

                } else if((i == (numCells/2) && (j == (numCells/2)-1))){
                        val cell = Cell("planet03")
                        matrix[i][j] = cell

                } else if((i == (numCells/2) && (j == (numCells/2)))){
                    val cell = Cell("planet04")
                    matrix[i][j] = cell
                } else{
                    val cell = Cell("path")
                    matrix[i][j] = cell

                }


            }
        }
        matrix_board = matrix
        setCharacter(character, numCells)
        setSpecialCells(numCells, "block", numBlocks)
        setSpecialCells(numCells, "bug", numBugs)
        setSpecialCells(numCells, "cpu", numCPUs)


    }

    private fun setCharacter(character: String?, numCells: Int){
        when(character){
            "kotlin" -> {
                val characterCell = Cell("kotlin")
                matrix_board[numCells-1][0] = characterCell
            }
            "ruby" -> {
                val characterCell = Cell("ruby")
                matrix_board[numCells-1][0] = characterCell
            }
        }
    }

    private fun setSpecialCells(numCells: Int, typeEnemy: String, numEnemies: Int){
        for (i in (0 until numEnemies)){
            val randomNumber1 = (0 until numCells).random()
            val randomNumber2 = (0 until numCells).random()
            val selectedCell = matrix_board[randomNumber1][randomNumber2]
            if ((selectedCell?.image.equals("path"))){
                val newCell = Cell(typeEnemy)
                matrix_board[randomNumber1][randomNumber2] = newCell
            } else {
                val newPosition = foundFreePath(numCells,0, 0)
                val newCell = Cell(typeEnemy)
                matrix_board[newPosition[0]][newPosition[1]] = newCell
            }
        }
    }

    private fun foundFreePath(numCells: Int, x: Int, y: Int): Array<Int>{
        if (matrix_board[x][y]?.image.equals( "path"))
            return arrayOf(x, y)
        else{
            var xResult = 0
            var yResult = 0
            if (x+1 < matrix_board[0].size) {
                val result = foundFreePath(numCells, x + 1, y)
                xResult = result[0]
                yResult = result[1]
            } else if (y+1 < matrix_board[0].size){
                val result= foundFreePath(numCells, x, y+1)
                xResult = result[0]
                yResult = result[1]
            }

            return arrayOf(xResult, yResult)
        }
    }

    private fun createVisualBoard(board: TableLayout, maxCells: Int, level: String?){
        for (i in (0 until maxCells)){
            val row = TableRow(this)
            row.removeAllViews()
            for (j in (0 until maxCells)){
                val cell = ImageView(this)
                when(level){
                    "easy" ->{
                        when (matrix_board[i][j]?.image){
                            "planet01" -> cell.setImageResource(R.drawable.bitrise_01_100x100)
                            "planet02" -> cell.setImageResource(R.drawable.bitrise_02_100x100)
                            "planet03" -> cell.setImageResource(R.drawable.bitrise_03_100x100)
                            "planet04" -> cell.setImageResource(R.drawable.bitrise_04_100x100)
                            "kotlin" -> cell.setImageResource(R.drawable.kotlin_100x100)
                            "ruby" -> cell.setImageResource(R.drawable.ruby_100x100)
                            else -> cell.setImageResource(R.drawable.back_path_100x100)
                        }

                    }
                    "medium" -> {
                        when (matrix_board[i][j]?.image){
                            "planet01" -> cell.setImageResource(R.drawable.bitrise_01_75x75)
                            "planet02" -> cell.setImageResource(R.drawable.bitrise_02_75x75)
                            "planet03" -> cell.setImageResource(R.drawable.bitrise_03_75x75)
                            "planet04" -> cell.setImageResource(R.drawable.bitrise_04_75x75)
                            "kotlin" -> cell.setImageResource(R.drawable.kotlin_75x75)
                            "ruby" -> cell.setImageResource(R.drawable.ruby_75x75)
                            else -> cell.setImageResource(R.drawable.back_path_75x75)
                        }
                    }
                    "hard" -> {
                        when (matrix_board[i][j]?.image){
                            "planet01" -> cell.setImageResource(R.drawable.bitrise_01_60x60)
                            "planet02" -> cell.setImageResource(R.drawable.bitrise_02_60x60)
                            "planet03" -> cell.setImageResource(R.drawable.bitrise_03_60x60)
                            "planet04" -> cell.setImageResource(R.drawable.bitrise_04_60x60)
                            "kotlin" -> cell.setImageResource(R.drawable.kotlin_60x60)
                            "ruby" -> cell.setImageResource(R.drawable.ruby_60x60)
                            else -> cell.setImageResource(R.drawable.back_path_60x60)
                        }
                    }

                }

                row.addView(cell)
            }
            board.addView(row)
        }
    }

    private fun setPlayerDeck(
        deck: Deck, card1: ImageView, card2: ImageView, card3: ImageView,
        card4: ImageView
    ) {
        dealCard(deck, card1)
        dealCard(deck, card2)
        dealCard(deck, card3)
        dealCard(deck, card4)


    }

    private fun setImageCard(card: String, cardImage: ImageView) {
        when (card) {
            "goForward" -> cardImage.setImageResource(R.drawable.card_go_forward)
            "right" -> cardImage.setImageResource(R.drawable.card_right)
            "left" -> cardImage.setImageResource(R.drawable.card_left)
            "turnAround" -> cardImage.setImageResource(R.drawable.card_turn_around)
            "None" -> cardImage.setImageResource(R.drawable.card_back)

        }

    }

    private fun dealCard(deck: Deck, cardImage: ImageView) {
        setImageCard(deck.dealCard(), cardImage)


    }

    private fun drawCards(deck: Deck, card: ImageView) {
        dealCard(deck, card)

    }


}
class Cell(var image: String)