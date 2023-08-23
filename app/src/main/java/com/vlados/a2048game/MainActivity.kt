package com.vlados.a2048game

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.vlados.a2048game.databinding.ActivityMainBinding
import java.io.InputStreamReader
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding

    var grid = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        spawnNumber()
        spawnNumber()
        updateScore()

        bindingClass.buttonUp.setOnClickListener {
            summUp()
            updateScore()
        }
        bindingClass.buttonDown.setOnClickListener {
            summDown()
            updateScore()
        }
        bindingClass.buttonLeft.setOnClickListener {
            summLeft()
            updateScore()
        }
        bindingClass.buttonRight.setOnClickListener {
            summRight()
            updateScore()
        }
        bindingClass.buttonRestart.setOnClickListener {
            restartGame()
        }

    }
fun restartGame (){
    grid = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )
    spawnNumber()
    spawnNumber()
    updateScore()
}

    fun processSumm (){
        var gridChanged = false
        for (gridIndex in 0..3) {
            val row = grid[gridIndex]
            val size = row.size
            var updateRow = IntArray(size)
            var finalUpdateRow = IntArray(size)
            var j = size-1
            for (i in (size-1) downTo  0){
                if (row[i] != 0) {
                    updateRow[j] = row[i]
                    j--
                }
            }
            for (i in (size - 1) downTo 1){
                if (updateRow[i] == updateRow[i-1] && updateRow[i] != 0){
                    updateRow[i] *= 2
                    updateRow[i-1] = 0
                }
            }
            j = size - 1
            for (i in (size-1) downTo  0){
                if (updateRow[i] != 0){
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
        if(gridChanged) {spawnNumber()}
    }

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

    fun updateScore() {
        var finalScore = 0
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                val cell = grid[i][j]
                finalScore += cell
                val rowView = bindingClass.mainContainer.children.toList()[i] as? TableRow
                val cellView = rowView?.children?.toList()?.get(j) as? TextView
               cellView?.text = cell.toString()
                cellView?.setBackgroundColor(selectColor(cell))
            }
        }
        bindingClass.score.text = finalScore.toString()
    }

    fun slideArray(array: Array<Int>) {
        for (i in array.indices) {
            val currentElementIsZero = array[i] == 0
            val nextElementIsNotZero = array[i + 1] != 0
            if (currentElementIsZero && nextElementIsNotZero) {
                array[i] = array[i + 1]
                array[i + 1] = 0
            }
        }
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
}

//fun processSumm (){
//    for (row in grid) {
//        Log.d("сумма", "Я пошел по строке")
//        for (i in row.indices.reversed()) {
//            Log.d("сумма", "Я прошелся по элементам в строке")
//            val nextIndex = i - 1
//            if (nextIndex < 0) break
//            val areElementsSame = row[i] == row[nextIndex]
//            val currentElementNotZero = row[i] != 0
//            val nextElementIsZero = row[nextIndex] == 0
//            val nextElementIsNotZero = row[nextIndex] != 0
//            val currentElementIsZero = row[i] == 0
//            if (areElementsSame && currentElementNotZero) {
//                row[i] *= 2
//                row[nextIndex] = 0
//                Log.d("сумма", "Отработал первый If")
//            }
//            else if (currentElementIsZero && nextElementIsNotZero) {
//                row[i] = row[nextIndex]
//                row[nextIndex] = 0
//                Log.d("сумма", "Отработал 2 ИФ")
//            }
//        }
//        Log.d("сумма", "Прошлись по всей строке, идем на следующую")
//    }
//    spawnNumber()
//    Log.d("сумма", "Закончили проходку, Заспавнили число")
//}


// ВТОРОЙ ВАРИАНТ






//fun processSumm (){
//    for (row in grid) {
//        var i = 0
//        while (i <= row.size){
//            val nextIndex = i + 1
//            if(nextIndex >= row.size )
//                break
//
//            val currentElement = row[i]
//            val nextElement = row[nextIndex]
//            if (nextElement == 0 && currentElement != 0){
//                row[nextIndex] = currentElement
//                row[i] = 0
//            }
//            if (currentElement == nextElement){
//                row[i] = 0
//                row[i+1] *= 2
//            }
//            i++
//        }
//    }
//    spawnNumber()
//}


//////////////////////////////////////////////
//fun processSumm (){
//    for (row in grid) {
//        for (i in row.indices) {
//            Log.d("сумма", "Я прошелся по элементам в строке, текущий row: ${row.contentToString()}")
//            val nextIndex = i + 1
//            if (nextIndex >= row.size) break
//
//            val currentElement = row[i]
//            val nextElement = row[nextIndex]
//            Log.d("сумма", "currentElement = $currentElement, nextElement = $nextElement")
//
//            if (currentElement == nextElement) {
//                row[i] = 0
//                row[nextIndex] *= 2
//                Log.d("сумма", "Отработал первый If текущий row: ${row.contentToString()}")
//            }
//            if (nextElement == 0 && currentElement !=0) {
//                row[nextIndex] = currentElement
//                row[i] = 0
//                Log.d("сумма", "Отработал 2 ИФ текущий row: ${row.contentToString()}")
//            }
//        }
//        Log.d("сумма", "Прошлись по всей строке, идем на следующую")
//    }
//    spawnNumber()
//    Log.d("сумма", "Закончили проходку, Заспавнили число")
//}

/////

//fun processSumm (): Array<IntArray> {
//    var updateGrid = arrayOf<IntArray>()
//    for (row in grid) {
//        val size = row.size
//        val updateRow = IntArray(size)
//        val finalUpdateRow = IntArray(size)
//        var j = size-1
//        for (i in (size-1) .. 0){
//            if (row[i] != 0){
//                updateRow[j] = row[i]
//                j--
//            }
//        }
//        for (i in (size - 1) ..1){
//            if (updateRow[i] == updateRow[i-1]){
//                updateRow[i] *= 2
//                updateRow[i-1] = 0
//            }
//        }
//        j = size - 1
//        for (i in (size - 1).. 0) {
//            if (updateRow[i] != 0) {
//                finalUpdateRow[j] = updateRow[i]
//                (j)--
//            }
//        }
//        updateGrid.fill(finalUpdateRow)
//    }
//    return updateGrid
//    spawnNumber()
//}



//fun processSumm (){
//    var gridChanged = false
//    for (gridIndex in 0..3) {
//        val row = grid[gridIndex]
//        val size = row.size
//        var updateRow = IntArray(size)
//        var j = size-1
//        for (i in (size-1) downTo  0){
//            if (row[i] != 0){
//                updateRow[j] = row[i]
//                j--
//            }
//        }
//        for (i in (size - 1) downTo 1){
//            if (updateRow[i] == updateRow[i-1]){
//                updateRow[i] *= 2
//                updateRow[i-1] = 0
//            }
//        }
//        j = size - 1
//        for (i in (size - 1)downTo  0) {
//            grid[gridIndex][j] = 0
//            if (updateRow[i] != 0) {
//                grid[gridIndex][j] = updateRow[i]
//                gridChanged = i != j
//                (j)--
//            }
//        }
//    }
//    if(gridChanged) {spawnNumber()}
//}