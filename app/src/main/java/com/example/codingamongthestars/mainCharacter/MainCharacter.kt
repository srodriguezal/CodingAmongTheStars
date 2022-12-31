package com.example.codingamongthestars.mainCharacter

class MainCharacter{
    private var name: String = ""
    private var positionRow: Int = 0
    private var positionCol: Int = 0
    var orientation: String = ""

    fun setStartPosition(numCells: Int){
        positionRow = numCells-1
        positionCol = 0
        orientation = "up"

    }

    fun setPosition(newRow: Int, newCol: Int){
        positionRow = newRow
        positionCol = newCol
    }

    fun setName(newName: String){
        name = newName
    }

    fun getPosition(): Array<Int>{
        return arrayOf(positionRow, positionCol)
    }

    fun getName(): String{
        return name
    }


}