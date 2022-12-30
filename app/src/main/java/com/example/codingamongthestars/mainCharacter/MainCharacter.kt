package com.example.codingamongthestars.mainCharacter

class MainCharacter{
    private var name: String? = null
    private var positionX: Int = 0
    private var positionY: Int = 0
    var orientation: String = ""

    fun setStartPosition(numCells: Int){
        positionX = numCells-1
        positionY = 0
        orientation = "up"

    }

    fun setPosition(newX: Int, newY: Int){
        positionX = newX
        positionY = newY
    }

    fun setName(newName: String?){
        name = newName
    }

    fun getPosition(): Array<Int>{
        return arrayOf(positionX, positionY)
    }

    fun getName(): String?{
        return name
    }


}