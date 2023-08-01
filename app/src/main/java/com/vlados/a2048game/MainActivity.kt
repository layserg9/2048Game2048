package com.vlados.a2048game

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.vlados.a2048game.databinding.ActivityMainBinding
import java.io.InputStreamReader
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
// 1. создаем двумерный массив

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

    }



fun processSumm (){
    for (row in grid) {
        for (i in row.indices) {
            val nextIndex = i + 1
            if (nextIndex > row.lastIndex) break
            val areElementsSame = row[i] == row[nextIndex]
            val currentElementNotZero = row[i] != 0
            val nextElementIsZero = row[nextIndex] == 0
            if (areElementsSame && currentElementNotZero) {
                row[nextIndex] *= 2
                row[i] = 0
            }
            if (currentElementNotZero && nextElementIsZero) {
                row[nextIndex] = row[i]
                row[i] = 0
            }
        }
    }
    spawnNumber()
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
            }
        }
        bindingClass.score.text = finalScore.toString()

        bindingClass.textView5.text = grid[0][0].toString()
        bindingClass.textView6.text = grid[0][1].toString()
        bindingClass.textView7.text = grid[0][2].toString()
        bindingClass.textView8.text = grid[0][3].toString()

        bindingClass.textView9.text = grid[1][0].toString()
        bindingClass.textView10.text = grid[1][1].toString()
        bindingClass.textView11.text = grid[1][2].toString()
        bindingClass.textView12.text = grid[1][3].toString()

        bindingClass.textView13.text = grid[2][0].toString()
        bindingClass.textView14.text = grid[2][1].toString()
        bindingClass.textView15.text = grid[2][2].toString()
        bindingClass.textView16.text = grid[2][3].toString()

        bindingClass.textView17.text = grid[3][0].toString()
        bindingClass.textView18.text = grid[3][1].toString()
        bindingClass.textView19.text = grid[3][2].toString()
        bindingClass.textView20.text = grid[3][3].toString()
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
}
