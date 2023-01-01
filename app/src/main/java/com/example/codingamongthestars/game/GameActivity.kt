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
    private var characterName: String? = null
    private var character: MainCharacter = MainCharacter()
    private var numMaxCellsInRow: Int = 0
    private var lives: Int = 3
    private var level: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)

        val bundle = intent.extras
        level = bundle?.getString("level")
        characterName = bundle?.getString("character")
        characterName?.let { character.setName(it) }
        setLivesImage()

        val backButton: ImageView = findViewById(R.id.backGameButton)
        backButton.setOnClickListener {
            backToSelectCharacter()
        }

        val helpButton: ImageView = findViewById(R.id.helpGameButton)
        helpButton.setOnClickListener {
            //openHelpScreen()
        }

        val board: TableLayout = findViewById(R.id.gameTableLayout)
        board.removeAllViews()
        setBoard(characterName, board)

        val discardDeckImage: ImageView = findViewById(R.id.imgViewDiscardDeck)
        val card1Image: ImageView = findViewById(R.id.imgViewCard1)
        card1Image.setOnClickListener {
            if (playerDeck.size == 4) {
                playCard(card1Image, 0, discardDeckImage)
            } else {
                //Mostrar error
            }

        }

        val card2Image: ImageView = findViewById(R.id.imgViewCard2)
        card2Image.setOnClickListener {
            if (playerDeck.size == 4) {
                playCard(card2Image, 1, discardDeckImage)
            } else {
                //Mostrar error
            }
        }

        val card3Image: ImageView = findViewById(R.id.imgViewCard3)
        card3Image.setOnClickListener {
            if (playerDeck.size == 4) {
                playCard(card3Image, 2, discardDeckImage)
            } else {
                //Mostrar error
            }
        }

        val card4Image: ImageView = findViewById(R.id.imgViewCard4)
        card4Image.setOnClickListener {
            if (playerDeck.size == 4) {
                playCard(card4Image, 3, discardDeckImage)
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
                discardDeckImage.setImageResource(R.drawable.discard_deck)

            } else {
                discardDeck.addCards(playerDeck)
                val lastCardDiscarded = discardDeck.getLastCard()
                drawImageCard(lastCardDiscarded, discardDeckImage)

            }
            playerDeck = setPlayerDeck(card1Image, card2Image, card3Image, card4Image)

        }

        val deckRollButton: ImageView = findViewById(R.id.deckButton)

        deckRollButton.setOnClickListener {
            if (playerDeck.size < 4) {
                val newCard: String = deck.dealCard()
                val invisibleCard: Pair<ImageView,Int> =
                    searchInvisibleCardInPlayerDeck(card1Image, card2Image, card3Image, card4Image)
                invisibleCard.first.visibility = View.VISIBLE
                drawImageCard(newCard, invisibleCard.first)
                playerDeck.add(invisibleCard.second, newCard)


            }

        }

        val restartButton: ImageView = findViewById(R.id.restartButton)
        restartButton.setOnClickListener {
            board.removeAllViews()
            setBoard(characterName, board)
            deck = Deck()
            discardDeck = DiscardDeck()
            playerDeck = setPlayerDeck(card1Image, card2Image, card3Image, card4Image)
            discardDeckImage.setImageResource(R.drawable.discard_deck)
            lives = 3
            setLivesImage()
        }


    }

    private fun backToSelectCharacter() {
        val backIntent = Intent(this, CharacterSelectionActivity::class.java)
        backIntent.putExtra("level", level)
        startActivity(backIntent)
    }

    private fun setLivesImage() {
        val livesImage: ImageView = findViewById(R.id.livesCounter)
        when (lives) {
            3 -> livesImage.setImageResource(R.drawable.life_three)
            2 -> livesImage.setImageResource(R.drawable.life_two)
            1 -> livesImage.setImageResource(R.drawable.life_one)
        }
    }

    private fun setBoard(characterName: String?, board: TableLayout) {
        when (level) {
            "easy" -> {
                numMaxCellsInRow = 4
                character.setStartPosition(numMaxCellsInRow)
                createMatrix(characterName, numMaxCellsInRow, 4, 1, 0)
                createVisualBoard(board, numMaxCellsInRow)

            }
            "medium" -> {
                numMaxCellsInRow = 6
                character.setStartPosition(numMaxCellsInRow)
                createMatrix(characterName, numMaxCellsInRow, 6, 2, 1)
                createVisualBoard(board, numMaxCellsInRow)

            }
            "hard" -> {
                numMaxCellsInRow = 8
                character.setStartPosition(numMaxCellsInRow)
                createMatrix(characterName, numMaxCellsInRow, 18, 4, 2)
                createVisualBoard(board, numMaxCellsInRow)

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
        val matrix = Array(numCells) { Array(numCells) { Cell("", -1) } }
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

    private fun createVisualBoard(board: TableLayout, maxCells: Int) {
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
                        drawEasyBoard(matrixBoard[i][j].image, cell)
                    }
                    "medium" -> {
                        drawMediumBoard(matrixBoard[i][j].image, cell)
                    }
                    "hard" -> {
                        drawHardBoard(matrixBoard[i][j].image, cell)
                    }

                }

                row.addView(cell)
                cellId++
            }
            board.addView(row)
        }
    }

    private fun drawEasyBoard(
        cellContent: String?,
        cellImageView: ImageView
    ) {
        when (cellContent) {
            "planet01" -> cellImageView.setImageResource(R.drawable.bitrise_01_100x100)
            "planet02" -> cellImageView.setImageResource(R.drawable.bitrise_02_100x100)
            "planet03" -> cellImageView.setImageResource(R.drawable.bitrise_03_100x100)
            "planet04" -> cellImageView.setImageResource(R.drawable.bitrise_04_100x100)
            "kotlin" -> cellImageView.setImageResource(R.drawable.kotlin_100x100)
            "ruby" -> cellImageView.setImageResource(R.drawable.ruby_100x100)
            else -> cellImageView.setImageResource(R.drawable.back_path_100x100)
        }

    }

    private fun drawMediumBoard(
        cellContent: String?,
        cellImageView: ImageView
    ) {
        when (cellContent) {
            "planet01" -> cellImageView.setImageResource(R.drawable.bitrise_01_75x75)
            "planet02" -> cellImageView.setImageResource(R.drawable.bitrise_02_75x75)
            "planet03" -> cellImageView.setImageResource(R.drawable.bitrise_03_75x75)
            "planet04" -> cellImageView.setImageResource(R.drawable.bitrise_04_75x75)
            "kotlin" -> cellImageView.setImageResource(R.drawable.kotlin_75x75)
            "ruby" -> cellImageView.setImageResource(R.drawable.ruby_75x75)
            else -> cellImageView.setImageResource(R.drawable.back_path_75x75)

        }

    }

    private fun drawHardBoard(
        cellContent: String?,
        cellImageView: ImageView
    ) {
        when (cellContent) {
            "planet01" -> cellImageView.setImageResource(R.drawable.bitrise_01_60x60)
            "planet02" -> cellImageView.setImageResource(R.drawable.bitrise_02_60x60)
            "planet03" -> cellImageView.setImageResource(R.drawable.bitrise_03_60x60)
            "planet04" -> cellImageView.setImageResource(R.drawable.bitrise_04_60x60)
            "kotlin" -> cellImageView.setImageResource(R.drawable.kotlin_60x60)
            "ruby" -> cellImageView.setImageResource(R.drawable.ruby_60x60)
            else -> cellImageView.setImageResource(R.drawable.back_path_60x60)

        }

    }

    private fun setPlayerDeck(
        card1: ImageView, card2: ImageView, card3: ImageView, card4: ImageView
    ): MutableList<String> {
        return if (!deck.isEmpty()) {
            card1.visibility = View.VISIBLE
            card2.visibility = View.VISIBLE
            card3.visibility = View.VISIBLE
            card4.visibility = View.VISIBLE
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
            drawImageCard(card, cardImage)
            card
        } else {
            "deckEmpty"
        }
    }

    private fun drawImageCard(card: String, cardImage: ImageView) {
        when (card) {
            "goForward" -> cardImage.setImageResource(R.drawable.card_go_forward)
            "right" -> cardImage.setImageResource(R.drawable.card_right)
            "left" -> cardImage.setImageResource(R.drawable.card_left)
            "down" -> cardImage.setImageResource(R.drawable.card_down)
            "up" -> cardImage.setImageResource(R.drawable.card_up)
            "None" -> cardImage.setImageResource(R.drawable.card_back)

        }

    }

    private fun searchInvisibleCardInPlayerDeck(
        card1: ImageView,
        card2: ImageView,
        card3: ImageView,
        card4: ImageView
    ): Pair<ImageView,Int> {
        return if (card1.visibility == View.GONE) Pair(card1, 0)
        else if (card2.visibility == View.GONE) Pair(card2, 1)
        else if (card3.visibility == View.GONE) Pair(card3,2)
        else Pair(card4,3)
    }

    private fun playCard(cardImage: ImageView, numCard: Int, discardDeckImage: ImageView) {
        val card = playerDeck[numCard]
        val characterPosition = character.getPosition()
        val characterCell: ImageView =
            findViewById(matrixBoard[characterPosition[0]][characterPosition[1]].id)

        when (card) {
            "goForward" -> {
                when (character.orientation) {
                    "right" -> {
                        val newPositionXRight = moveXCharacter(1, characterPosition)
                        drawRightCharacter(newPositionXRight)

                    }
                    "left" -> {
                        val newPositionXLeft = moveXCharacter(-1, characterPosition)
                        drawLeftCharacter(newPositionXLeft)

                    }
                    "up" -> {
                        val newPositionYUp = moveYCharacter(-1, characterPosition)
                        drawUpCharacter(newPositionYUp)
                    }
                    "down" -> {
                        val newPositionYDown = moveYCharacter(1, characterPosition)
                        drawDownCharacter(newPositionYDown)
                    }
                }

            }
            "right" -> {
                character.orientation = "right"
                drawRightCharacter(characterCell)

            }
            "left" -> {
                character.orientation = "left"
                drawLeftCharacter(characterCell)

            }
            "down" -> {
                character.orientation = "down"
                drawDownCharacter(characterCell)
            }
            "up" -> {
                character.orientation = "up"
                drawUpCharacter(characterCell)
            }
        }
        discardCard(numCard, cardImage, discardDeckImage)
    }

    private fun discardCard(numCard: Int, cardImage: ImageView, discardDeckImage: ImageView) {
        cardImage.visibility = View.GONE
        discardDeck.discardCard(playerDeck[numCard])
        drawImageCard(playerDeck[numCard], discardDeckImage)
        playerDeck.removeAt(numCard)


    }

    private fun moveXCharacter(x: Int, characterPosition: Array<Int>): ImageView {
        val move = characterPosition[1] + x

        if ((move < numMaxCellsInRow) && (move >= 0)) {

            val targetCell: Cell = matrixBoard[characterPosition[0]][move]
            val currentCell: Cell = matrixBoard[characterPosition[0]][characterPosition[1]]
            val pathCell: ImageView = findViewById(currentCell.id)

            when (targetCell.image) {
                "bug" -> {
                    findBugCell(characterPosition, targetCell)

                }
                "cpu" -> {
                    findCPUCell(characterPosition, targetCell)
                }
                "block" -> {
                    findBlockCell(targetCell)
                }
                else -> {
                    val newCell =
                        Cell(character.getName(), matrixBoard[characterPosition[0]][move].id)
                    matrixBoard[characterPosition[0]][move] = newCell

                    val oldCell =
                        Cell("path", matrixBoard[characterPosition[0]][characterPosition[1]].id)
                    matrixBoard[characterPosition[0]][characterPosition[1]] = oldCell
                    character.setPosition(characterPosition[0], move)

                }
            }

            drawPath(pathCell)

            if (canMove(targetCell)) {
                // DO THINGS
            }

            if (targetCell.image.contains("planet")) {
                winGame()
            }
        } else {
            //mostrar error
        }
        val newPosition = character.getPosition()
        return findViewById(matrixBoard[newPosition[0]][newPosition[1]].id)
    }

    private fun moveYCharacter(y: Int, characterPosition: Array<Int>): ImageView {
        val move = characterPosition[0] + y
        if ((move < numMaxCellsInRow) && (move >= 0)) {

            val targetCell: Cell = matrixBoard[move][characterPosition[1]]
            val currentCell: Cell = matrixBoard[characterPosition[0]][characterPosition[1]]
            val pathCell: ImageView = findViewById(currentCell.id)

            when (targetCell.image) {
                "bug" -> {
                    findBugCell(characterPosition, targetCell)

                }
                "cpu" -> {
                    findCPUCell(characterPosition, targetCell)
                }
                "block" -> {
                    findBlockCell(targetCell)
                }
                else -> {
                    val newCell =
                        Cell(character.getName(), matrixBoard[move][characterPosition[1]].id)
                    matrixBoard[move][characterPosition[1]] = newCell

                    val oldCell =
                        Cell("path", matrixBoard[characterPosition[0]][characterPosition[1]].id)
                    matrixBoard[characterPosition[0]][characterPosition[1]] = oldCell
                    character.setPosition(move, characterPosition[1])
                }
            }

            drawPath(pathCell)

            if (targetCell.image.contains("planet")) {
                winGame()
            }
        } else {
            //mostrar error
        }

        val newPosition = character.getPosition()
        return findViewById(matrixBoard[newPosition[0]][newPosition[1]].id)
    }

    private fun canMove(targetCell: Cell): Boolean {
        return targetCell.image == "block"
    }

    private fun findBlockCell(targetCell: Cell) {
        val blockCell: ImageView = findViewById(targetCell.id)
        drawBlock(blockCell)
    }

    private fun findCPUCell(
        characterPosition: Array<Int>,
        targetCell: Cell
    ) {
        lives -= 1
        setLivesImage()
        checkIfUserLost()

        val newCell =
            Cell(character.getName(), matrixBoard[numMaxCellsInRow - 1][0].id)
        matrixBoard[numMaxCellsInRow - 1][0] = newCell
        val oldCell =
            Cell("path", matrixBoard[characterPosition[0]][characterPosition[1]].id)
        matrixBoard[characterPosition[0]][characterPosition[1]] = oldCell

        val cpuCell: ImageView = findViewById(targetCell.id)
        drawCPU(cpuCell)
        character.setPosition(numMaxCellsInRow - 1, 0)
    }

    private fun findBugCell(
        characterPosition: Array<Int>,
        targetCell: Cell
    ) {
        lives -= 1
        setLivesImage()
        checkIfUserLost()
        val newPosition = findNewPosition()

        val newCell = Cell(character.getName(), matrixBoard[newPosition[0]][newPosition[1]].id)
        matrixBoard[newPosition[0]][newPosition[1]] = newCell

        val oldCell =
            Cell("path", matrixBoard[characterPosition[0]][characterPosition[1]].id)
        matrixBoard[characterPosition[0]][characterPosition[1]] = oldCell

        val bugCell: ImageView = findViewById(targetCell.id)
        drawBug(bugCell)
        character.setPosition(newPosition[0], newPosition[1])
    }

    private fun findNewPosition(): Array<Int> {
        val newX = (0 until numMaxCellsInRow).random()
        val newY = (0 until numMaxCellsInRow).random()
        val newPosition = arrayOf(newX, newY)

        return if ((newX == ((numMaxCellsInRow / 2) - 1) && (newY == (numMaxCellsInRow / 2) - 1)) ||
            ((newX == (numMaxCellsInRow / 2 - 1)) && newY == numMaxCellsInRow / 2) ||
            (newX == numMaxCellsInRow / 2 && newY == ((numMaxCellsInRow / 2) - 1)) ||
            (newX == numMaxCellsInRow / 2 && newY == numMaxCellsInRow / 2)
        ) {
            findNewPosition()
        } else {
            newPosition
        }
    }

    private fun drawRightCharacter(characterCell: ImageView) {
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

    private fun drawLeftCharacter(characterCell: ImageView) {
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

    private fun drawUpCharacter(characterCell: ImageView) {
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

    private fun drawDownCharacter(characterCell: ImageView) {
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

    private fun drawPath(imageCell: ImageView) {
        when (level) {
            "easy" -> imageCell.setImageResource(R.drawable.path_100x100)
            "medium" -> imageCell.setImageResource(R.drawable.path_75x75)
            "hard" -> imageCell.setImageResource(R.drawable.path_60x60)
        }

    }

    private fun drawBlock(imageCell: ImageView) {
        when (level) {
            "easy" -> imageCell.setImageResource(R.drawable.bricks_100x100)
            "medium" -> imageCell.setImageResource(R.drawable.bricks_75x75)
            "hard" -> imageCell.setImageResource(R.drawable.bricks_60x60)
        }

    }

    private fun drawBug(imageCell: ImageView) {
        when (level) {
            "easy" -> imageCell.setImageResource(R.drawable.bug_100x100)
            "medium" -> imageCell.setImageResource(R.drawable.bug_75x75)
            "hard" -> imageCell.setImageResource(R.drawable.bug_60x60)
        }

    }

    private fun drawCPU(imageCell: ImageView) {
        when (level) {
            "medium" -> imageCell.setImageResource(R.drawable.cpu_75x75)
            "hard" -> imageCell.setImageResource(R.drawable.cpu_60x60)
        }

    }

    private fun checkIfUserLost() {
        if (lives == 0) {
            val failIntent = Intent(this, LostGameActivity::class.java)
            failIntent.putExtra("level", level)
            failIntent.putExtra("character", characterName)
            startActivity(failIntent)

        }
    }

    private fun winGame() {
        val winIntent = Intent(this, WinGameActivity::class.java)
        startActivity(winIntent)

    }


}

class Cell(var image: String, var id: Int)