package com.vlados.a2048game

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private var grid = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    fun getCell(rowIndex: Int, cellIndex:Int): Int{
        return grid[rowIndex][cellIndex]
    }

    init {
        restartGame()
    }

    fun processSumm() {
        var gridChanged = false
        for (gridIndex in 0..3) {
            val row = grid[gridIndex]
            val size = row.size
            var updateRow = IntArray(size)
            var finalUpdateRow = IntArray(size)
            var j = size - 1
            for (i in (size - 1) downTo 0) {
                if (row[i] != 0) {
                    updateRow[j] = row[i]
                    j--
                }
            }
            for (i in (size - 1) downTo 1) {
                if (updateRow[i] == updateRow[i - 1] && updateRow[i] != 0) {
                    updateRow[i] *= 2
                    updateRow[i - 1] = 0
                }
            }
            j = size - 1
            for (i in (size - 1) downTo 0) {
                if (updateRow[i] != 0) {
                    finalUpdateRow[j] = updateRow[i]
                    j--
                }
            }
            for (i in 0..3) {
                if (grid[gridIndex][i] != finalUpdateRow[i]) {
                    gridChanged = true
                }
            }
            grid[gridIndex] = finalUpdateRow.toTypedArray()
        }
        if (gridChanged) {
            spawnNumber()
        }
    }
    //TODO Исправить функцию processSumm избавиться от вложенных циклов for.

    fun summUp() {
        rotateArrayRight()
        processSumm()
        for (i in 0..2) {
            rotateArrayRight()
        }
    }

    fun summDown() {
        for (i in 0..2) {
            rotateArrayRight()
        }
        processSumm()
        rotateArrayRight()
    }

    fun summRight() {
        processSumm()
    }

    fun summLeft() {
        for (i in 0..1) {
            rotateArrayRight()
        }
        processSumm()
        for (i in 0..1) {
            rotateArrayRight()
        }
    }

    fun rotateArrayRight() {
        val numRows = grid.size
        val numCols = grid[0].size
        val rotatedArray = Array(numRows) { Array(numCols) { 0 } }
        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                rotatedArray[j][numRows - 1 - i] = grid[i][j]
            }
        }
        grid = rotatedArray
    }

    fun spawnNumber() {
        val mapOfCoordinates = mutableMapOf<Int, Int>()
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                val cell = grid[i][j]
                if (cell == 0) {
                    mapOfCoordinates.put(i, j)
                }
            }
        }
        if (mapOfCoordinates.isEmpty()) {
            return
        }
        val emptyCellCoordinate = mapOfCoordinates.entries.random()
        grid[emptyCellCoordinate.key][emptyCellCoordinate.value] = if (Math.random() > 0.1) 2 else 4
    }

    fun selectColor(number: Int): Int {
        return when (number) {
            2 -> 0xFFE1DFDE.toInt()
            4 -> 0xFFCAAF8C.toInt()
            8 -> 0xFFFFA98C.toInt()
            16 -> 0xFFFA8761.toInt()
            32 -> 0xFFFF5A5A.toInt()
            64 -> 0xFFFF5015.toInt()
            128 -> 0xFFFFD969.toInt()
            256 -> 0xFFFFCD36.toInt()
            512 -> 0xFFFDC61E.toInt()
            1024 -> 0xFF5C3A11.toInt()
            2048 -> 0xFF372E2B.toInt()
            else -> 0xFFB5B2B1.toInt()
        }
    }

    fun restartGame() {
        grid = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        spawnNumber()
        spawnNumber()
    }
}