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
import com.example.codingamongthestars.deck.DiscardDeck
import com.example.codingamongthestars.mainCharacter.MainCharacter


class GameActivity : AppCompatActivity() {
    private var matrixBoard: Array<Array<Cell>> = emptyArray()
    private var deck: Deck = Deck()
    private var discardDeck: DiscardDeck = DiscardDeck()
    private var playerDeck: MutableList<String> = mutableListOf()
    private var character: MainCharacter = MainCharacter()
    private var numMaxCellsInRow: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)

        val bundle = intent.extras
        val level = bundle?.getString("level")
        val characterName = bundle?.getString("character")
        character.setName(characterName)

        val backButton: ImageView = findViewById(R.id.backGameButton)
        backButton.setOnClickListener {
            backToSelectCharacter(level)
        }

        val helpButton: ImageView = findViewById(R.id.helpGameButton)
        helpButton.setOnClickListener {
            //openHelpScreen()
        }

        val board: TableLayout = findViewById(R.id.gameTableLayout)
        board.removeAllViews()
        setBoard(level, characterName, board)

        val discardDeckImage: ImageView = findViewById(R.id.imgViewDiscardDeck)
        val newCardImage: ImageView = findViewById(R.id.imgViewNewCard)
        val card1Image: ImageView = findViewById(R.id.imgViewCard1)
        card1Image.setOnClickListener {
            if (playerDeck.size == 4) {
                playCard(card1Image, 0, level)
            } else {
                //Mostrar error
            }

        }

        val card2Image: ImageView = findViewById(R.id.imgViewCard2)
        card2Image.setOnClickListener {
            if (playerDeck.size == 4) {
                playCard(card2Image, 1, level)
            } else {
                //Mostrar error
            }
        }

        val card3Image: ImageView = findViewById(R.id.imgViewCard3)
        card3Image.setOnClickListener {
            if (playerDeck.size == 4) {
                playCard(card3Image, 2, level)
            } else {
                //Mostrar error
            }
        }

        val card4Image: ImageView = findViewById(R.id.imgViewCard4)
        card4Image.setOnClickListener {
            if (playerDeck.size == 4) {
                playCard(card4Image, 3, level)
            } else {
                //Mostrar error
            }
        }

        playerDeck = setPlayerDeck(card1Image, card2Image, card3Image, card4Image)

        val trashButton: ImageView = findViewById(R.id.trashButton)
        trashButton.setOnClickListener {
            if (deck.isEmpty()) {
                deck = Deck()
                discardDeck = DiscardDeck()
                discardDeckImage.setImageResource(R.drawable.cementary)

            } else {
                discardDeck.addCards(playerDeck)
                val lastCardDiscarded = discardDeck.getLastCard()
                setImageCard(lastCardDiscarded, discardDeckImage)

            }
            playerDeck = setPlayerDeck(card1Image, card2Image, card3Image, card4Image)
            println(deck.size())

        }

        val deckRollButton: ImageView = findViewById(R.id.deckButton)

        deckRollButton.setOnClickListener {
            newCardImage.visibility = View.VISIBLE
            drawNewCard(newCardImage)
        }

        val restartButton: ImageView = findViewById(R.id.restartButton)
        restartButton.setOnClickListener {
            board.removeAllViews()
            setBoard(level, characterName, board)
            deck = Deck()
            discardDeck = DiscardDeck()
            playerDeck = setPlayerDeck(card1Image, card2Image, card3Image, card4Image)
            discardDeckImage.setImageResource(R.drawable.cementary)
            if (newCardImage.visibility == View.VISIBLE) {
                newCardImage.visibility = View.GONE
            }
        }


    }

    private fun backToSelectCharacter(level: String?) {
        val backIntent = Intent(this, CharacterSelectionActivity::class.java)
        backIntent.putExtra("level", level)
        startActivity(backIntent)
    }

    private fun setBoard(level: String?, characterName: String?, board: TableLayout) {
        when (level) {
            "easy" -> {
                numMaxCellsInRow = 4
                character.setStartPosition(numMaxCellsInRow)
                createMatrix(characterName, numMaxCellsInRow, 4, 1, 0)
                createVisualBoard(board, numMaxCellsInRow, level)

            }
            "medium" -> {
                numMaxCellsInRow = 6
                character.setStartPosition(numMaxCellsInRow)
                createMatrix(characterName, numMaxCellsInRow, 6, 2, 1)
                createVisualBoard(board, numMaxCellsInRow, level)

            }
            "hard" -> {
                numMaxCellsInRow = 8
                character.setStartPosition(numMaxCellsInRow)
                createMatrix(characterName, numMaxCellsInRow, 18, 4, 2)
                createVisualBoard(board, numMaxCellsInRow, level)

            }
        }
    }

    private fun createMatrix(
        characterName: String?,
        numCells: Int,
        numBlocks: Int,
        numBugs: Int,
        numCPUs: Int
    ) {
        val matrix = Array(numCells) { Array(numCells) { Cell(null, -1) } }
        for (i in (0 until numCells)) {
            for (j in (0 until numCells)) {
                if ((i == (numCells / 2) - 1) && (j == (numCells / 2) - 1)) {
                    val cell = Cell("planet01", -1)
                    matrix[i][j] = cell

                } else if ((i == (numCells / 2) - 1) && (j == (numCells / 2))) {
                    val cell = Cell("planet02", -1)
                    matrix[i][j] = cell

                } else if ((i == (numCells / 2) && (j == (numCells / 2) - 1))) {
                    val cell = Cell("planet03", -1)
                    matrix[i][j] = cell

                } else if ((i == (numCells / 2) && (j == (numCells / 2)))) {
                    val cell = Cell("planet04", -1)
                    matrix[i][j] = cell
                } else {
                    val cell = Cell("path", -1)
                    matrix[i][j] = cell

                }


            }
        }
        matrixBoard = matrix
        setCharacterInMatrix(characterName, numCells)
        setSpecialCells(numCells, "block", numBlocks)
        setSpecialCells(numCells, "bug", numBugs)
        setSpecialCells(numCells, "cpu", numCPUs)


    }

    private fun setCharacterInMatrix(characterName: String?, numCells: Int) {
        when (characterName) {
            "kotlin" -> {
                val characterCell = Cell("kotlin", -1)
                matrixBoard[numCells - 1][0] = characterCell
            }
            "ruby" -> {
                val characterCell = Cell("ruby", -1)
                matrixBoard[numCells - 1][0] = characterCell
            }
        }
    }

    private fun setSpecialCells(numCells: Int, typeEnemy: String, numEnemies: Int) {
        for (i in (0 until numEnemies)) {
            val randomNumber1 = (0 until numCells).random()
            val randomNumber2 = (0 until numCells).random()
            val selectedCell = matrixBoard[randomNumber1][randomNumber2]
            if ((selectedCell.image == "path")) {
                val newCell = Cell(typeEnemy, -1)
                matrixBoard[randomNumber1][randomNumber2] = newCell
            } else {
                val newPosition = foundFreePath(numCells, 0, 0)
                val newCell = Cell(typeEnemy, -1)
                matrixBoard[newPosition[0]][newPosition[1]] = newCell
            }
        }
    }

    private fun foundFreePath(numCells: Int, x: Int, y: Int): Array<Int> {
        if (matrixBoard[x][y].image == "path")
            return arrayOf(x, y)
        else {
            var xResult = 0
            var yResult = 0
            if (x + 1 < matrixBoard[0].size) {
                val result = foundFreePath(numCells, x + 1, y)
                xResult = result[0]
                yResult = result[1]
            } else if (y + 1 < matrixBoard[0].size) {
                val result = foundFreePath(numCells, x, y + 1)
                xResult = result[0]
                yResult = result[1]
            }

            return arrayOf(xResult, yResult)
        }
    }

    private fun createVisualBoard(board: TableLayout, maxCells: Int, level: String?) {
        var cellId = 0
        for (i in (0 until maxCells)) {
            val row = TableRow(this)
            row.removeAllViews()
            for (j in (0 until maxCells)) {
                val cell = ImageView(this)
                cell.id = cellId
                matrixBoard[i][j].id = cell.id
                when (level) {
                    "easy" -> {
                        showCellEasyLevel(matrixBoard[i][j].image, cell, false)
                    }
                    "medium" -> {
                        showCellMediumLevel(matrixBoard[i][j].image, cell, true)
                    }
                    "hard" -> {
                        showCellHardLevel(matrixBoard[i][j].image, cell, true)
                    }

                }

                row.addView(cell)
                cellId++
            }
            board.addView(row)
        }
    }

    private fun showCellEasyLevel(
        cellContent: String?,
        cellImageView: ImageView,
        settingBoard: Boolean
    ) {
        when (cellContent) {
            "planet01" -> cellImageView.setImageResource(R.drawable.bitrise_01_100x100)
            "planet02" -> cellImageView.setImageResource(R.drawable.bitrise_02_100x100)
            "planet03" -> cellImageView.setImageResource(R.drawable.bitrise_03_100x100)
            "planet04" -> cellImageView.setImageResource(R.drawable.bitrise_04_100x100)
            "kotlin" -> cellImageView.setImageResource(R.drawable.kotlin_100x100)
            "ruby" -> cellImageView.setImageResource(R.drawable.ruby_100x100)
            "path" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.path_100x100)
                } else cellImageView.setImageResource(R.drawable.back_path_100x100)
            }
            "bug" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.bug_100x100)
                } else cellImageView.setImageResource(R.drawable.back_path_100x100)
            }
            "block" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.bricks_100x100)
                } else cellImageView.setImageResource(R.drawable.back_path_100x100)
            }

        }


    }

    private fun showCellMediumLevel(
        cellContent: String?,
        cellImageView: ImageView,
        settingBoard: Boolean
    ) {
        when (cellContent) {
            "planet01" -> cellImageView.setImageResource(R.drawable.bitrise_01_75x75)
            "planet02" -> cellImageView.setImageResource(R.drawable.bitrise_02_75x75)
            "planet03" -> cellImageView.setImageResource(R.drawable.bitrise_03_75x75)
            "planet04" -> cellImageView.setImageResource(R.drawable.bitrise_04_75x75)
            "kotlin" -> cellImageView.setImageResource(R.drawable.kotlin_75x75)
            "ruby" -> cellImageView.setImageResource(R.drawable.ruby_75x75)
            "path" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.path_75x75)
                } else {
                    cellImageView.setImageResource(R.drawable.back_path_75x75)
                }
            }
            "bug" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.bug_75x75)
                } else {
                    cellImageView.setImageResource(R.drawable.back_path_75x75)
                }
            }
            "cpu" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.cpu_75x75)
                } else {
                    cellImageView.setImageResource(R.drawable.back_path_75x75)
                }
            }
            "block" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.bricks_75x75)
                } else {
                    cellImageView.setImageResource(R.drawable.back_path_75x75)
                }
            }

        }

    }

    private fun showCellHardLevel(
        cellContent: String?,
        cellImageView: ImageView,
        settingBoard: Boolean
    ) {
        when (cellContent) {
            "planet01" -> cellImageView.setImageResource(R.drawable.bitrise_01_60x60)
            "planet02" -> cellImageView.setImageResource(R.drawable.bitrise_02_60x60)
            "planet03" -> cellImageView.setImageResource(R.drawable.bitrise_03_60x60)
            "planet04" -> cellImageView.setImageResource(R.drawable.bitrise_04_60x60)
            "kotlin" -> cellImageView.setImageResource(R.drawable.kotlin_60x60)
            "ruby" -> cellImageView.setImageResource(R.drawable.ruby_60x60)
            "path" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.path_60x60)
                } else {
                    cellImageView.setImageResource(R.drawable.back_path_60x60)
                }
            }
            "bug" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.bug_60x60)
                } else {
                    cellImageView.setImageResource(R.drawable.back_path_60x60)
                }
            }
            "cpu" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.cpu_60x60)
                } else {
                    cellImageView.setImageResource(R.drawable.back_path_60x60)
                }
            }
            "block" -> {
                if (!settingBoard) {
                    cellImageView.setImageResource(R.drawable.bricks_60x60)
                } else {
                    cellImageView.setImageResource(R.drawable.back_path_60x60)
                }
            }

        }

    }


    private fun setPlayerDeck(
        card1: ImageView, card2: ImageView, card3: ImageView, card4: ImageView
    ): MutableList<String> {
        return if (!deck.isEmpty()) {
            mutableListOf(
                dealCard(card1),
                dealCard(card2),
                dealCard(card3),
                dealCard(card4)
            )
        } else
            mutableListOf()
    }

    private fun dealCard(cardImage: ImageView): String {
        return if (!deck.isEmpty()) {
            val card = deck.dealCard()
            setImageCard(card, cardImage)
            card
        } else {
            "deckEmpty"
        }
    }

    private fun setImageCard(card: String, cardImage: ImageView) {
        when (card) {
            "goForward" -> cardImage.setImageResource(R.drawable.card_go_forward)
            "right" -> cardImage.setImageResource(R.drawable.card_right)
            "left" -> cardImage.setImageResource(R.drawable.card_left)
            "down" -> cardImage.setImageResource(R.drawable.card_down)
            "up" -> cardImage.setImageResource(R.drawable.card_up)
            "None" -> cardImage.setImageResource(R.drawable.card_back)

        }

    }

    private fun drawNewCard(card: ImageView) {
        dealCard(card)

    }

    private fun playCard(cardImage: ImageView, numCard: Int, level: String?) {
        //cardImage.visibility = View.GONE
        val card = playerDeck[numCard]
        //playerDeck.removeAt(numCard)
        val characterPosition = character.getPosition()

        val characterCell: ImageView =
            findViewById(matrixBoard[characterPosition[0]][characterPosition[1]].id)
        when (card) {
            "goForward" -> {
                when (character.orientation) {
                    "right" -> {
                        moveXCharacter(1, characterPosition, level)

                    }
                    "left" -> {
                        moveXCharacter(-1, characterPosition, level)
                    }
                    "up" -> {
                        moveYCharacter(-1, characterPosition, level)
                    }
                    "down" -> {
                        moveYCharacter(1, characterPosition, level)
                    }
                }

            }
            "right" -> {
                character.orientation = "right"
                if (character.getName() == "kotlin") {
                    when (level) {
                        "easy" -> characterCell.setImageResource(R.drawable.kotlin_right_100x100)
                        "medium" -> characterCell.setImageResource(R.drawable.kotlin_right_75x75)
                        "hard" -> characterCell.setImageResource(R.drawable.kotlin_right_60x60)
                    }

                } else {
                    when (level) {
                        "easy" -> characterCell.setImageResource(R.drawable.ruby_right_100x100)
                        "medium" -> characterCell.setImageResource(R.drawable.ruby_right_75x75)
                        "hard" -> characterCell.setImageResource(R.drawable.ruby_right_60x60)
                    }
                }

            }
            "left" -> {
                character.orientation = "left"
                if (character.getName() == "kotlin") {
                    when (level) {
                        "easy" -> characterCell.setImageResource(R.drawable.kotlin_left_100x100)
                        "medium" -> characterCell.setImageResource(R.drawable.kotlin_left_75x75)
                        "hard" -> characterCell.setImageResource(R.drawable.kotlin_left_60x60)
                    }

                } else {
                    when (level) {
                        "easy" -> characterCell.setImageResource(R.drawable.ruby_left_100x100)
                        "medium" -> characterCell.setImageResource(R.drawable.ruby_left_75x75)
                        "hard" -> characterCell.setImageResource(R.drawable.ruby_left_60x60)
                    }
                }

            }
            "down" -> {
                character.orientation = "down"
                if (character.getName() == "kotlin") {
                    when (level) {
                        "easy" -> characterCell.setImageResource(R.drawable.kotlin_back_100x100)
                        "medium" -> characterCell.setImageResource(R.drawable.kotlin_back_75x75)
                        "hard" -> characterCell.setImageResource(R.drawable.kotlin_back_60x60)
                    }

                } else {
                    when (level) {
                        "easy" -> characterCell.setImageResource(R.drawable.ruby_back_100x100)
                        "medium" -> characterCell.setImageResource(R.drawable.ruby_back_75x75)
                        "hard" -> characterCell.setImageResource(R.drawable.ruby_back_60x60)
                    }
                }
            }
            "up" -> {
                character.orientation = "down"
                if (character.getName() == "kotlin") {
                    when (level) {
                        "easy" -> characterCell.setImageResource(R.drawable.kotlin_100x100)
                        "medium" -> characterCell.setImageResource(R.drawable.kotlin_75x75)
                        "hard" -> characterCell.setImageResource(R.drawable.kotlin_60x60)
                    }

                } else {
                    when (level) {
                        "easy" -> characterCell.setImageResource(R.drawable.ruby_100x100)
                        "medium" -> characterCell.setImageResource(R.drawable.ruby_75x75)
                        "hard" -> characterCell.setImageResource(R.drawable.ruby_60x60)
                    }
                }
            }
        }
    }

    private fun moveXCharacter(x: Int, characterPosition: Array<Int>, level: String?) {
        val move = characterPosition[0] + x
        if (move < numMaxCellsInRow) {
            val newCell = Cell(character.getName(), matrixBoard[move][characterPosition[1]].id)
            matrixBoard[move][characterPosition[1]] = newCell
            val oldCell = Cell("path", matrixBoard[characterPosition[0]][characterPosition[1]].id)
            matrixBoard[characterPosition[0]][characterPosition[1]] = oldCell

        } else {
            //mostrar error
        }

        when (level) {
            "easy" -> {
                val imageNewCell: ImageView =
                    findViewById(matrixBoard[move][characterPosition[1]].id)
                showCellEasyLevel(
                    matrixBoard[move][characterPosition[1]].image,
                    imageNewCell,
                    false
                )

                val imageOldCell: ImageView =
                    findViewById(matrixBoard[characterPosition[0]][characterPosition[1]].id)
                showCellEasyLevel(
                    matrixBoard[characterPosition[0]][characterPosition[1]].image,
                    imageOldCell,
                    false
                )
            }
            "medium" -> {
                val imageNewCell: ImageView =
                    findViewById(matrixBoard[move][characterPosition[1]].id)
                showCellMediumLevel(
                    matrixBoard[move][characterPosition[1]].image,
                    imageNewCell,
                    false
                )

                val imageOldCell: ImageView =
                    findViewById(matrixBoard[characterPosition[0]][characterPosition[1]].id)
                showCellMediumLevel(
                    matrixBoard[characterPosition[0]][characterPosition[1]].image,
                    imageOldCell,
                    false
                )

            }
            "hard" -> {
                val imageNewCell: ImageView =
                    findViewById(matrixBoard[move][characterPosition[1]].id)
                showCellHardLevel(
                    matrixBoard[move][characterPosition[1]].image,
                    imageNewCell,
                    false
                )

                val imageOldCell: ImageView =
                    findViewById(matrixBoard[characterPosition[0]][characterPosition[1]].id)
                showCellHardLevel(
                    matrixBoard[characterPosition[0]][characterPosition[1]].image,
                    imageOldCell,
                    false
                )
            }
        }

    }

    private fun moveYCharacter(y: Int, characterPosition: Array<Int>, level: String?) {
        val move = characterPosition[1] + y
        if (move < numMaxCellsInRow) {
            val newCell = Cell(character.getName(), matrixBoard[characterPosition[0]][move].id)
            matrixBoard[characterPosition[0]][move] = newCell
            val oldCell = Cell("path", matrixBoard[characterPosition[0]][characterPosition[1]].id)
            matrixBoard[characterPosition[0]][characterPosition[1]] = oldCell

        } else {
            //mostrar error
        }
        when (level) {
            "easy" -> {
                val imageNewCell: ImageView =
                    findViewById(matrixBoard[characterPosition[0]][move].id)
                showCellEasyLevel(
                    matrixBoard[characterPosition[0]][move].image,
                    imageNewCell,
                    false
                )

                val imageOldCell: ImageView =
                    findViewById(matrixBoard[characterPosition[0]][characterPosition[1]].id)
                showCellEasyLevel(
                    matrixBoard[characterPosition[0]][characterPosition[1]].image,
                    imageOldCell,
                    false
                )
            }
            "medium" -> {
                val imageNewCell: ImageView =
                    findViewById(matrixBoard[characterPosition[0]][move].id)
                showCellMediumLevel(
                    matrixBoard[characterPosition[0]][move].image,
                    imageNewCell,
                    false
                )

                val imageOldCell: ImageView =
                    findViewById(matrixBoard[characterPosition[0]][characterPosition[1]].id)
                showCellMediumLevel(
                    matrixBoard[characterPosition[0]][characterPosition[1]].image,
                    imageOldCell,
                    false
                )

            }
            "hard" -> {
                val imageNewCell: ImageView =
                    findViewById(matrixBoard[characterPosition[0]][move].id)
                showCellHardLevel(
                    matrixBoard[characterPosition[0]][move].image,
                    imageNewCell,
                    false
                )
                val imageOldCell: ImageView =
                    findViewById(matrixBoard[characterPosition[0]][characterPosition[1]].id)
                showCellHardLevel(
                    matrixBoard[characterPosition[0]][characterPosition[1]].image,
                    imageOldCell,
                    false
                )
            }
        }

    }


}

class Cell(var image: String?, var id: Int)