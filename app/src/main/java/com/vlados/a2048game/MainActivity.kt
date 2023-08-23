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
import androidx.activity.viewModels
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.vlados.a2048game.databinding.ActivityMainBinding
import java.io.InputStreamReader
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    private val logicModel: Logic by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        logicModel.spawnNumber()
        logicModel.spawnNumber()
        updateScore()

        bindingClass.buttonUp.setOnClickListener {
            logicModel.summUp()
            updateScore()
        }
        bindingClass.buttonDown.setOnClickListener {
            logicModel.summDown()
            updateScore()
        }
        bindingClass.buttonLeft.setOnClickListener {
            logicModel.summLeft()
            updateScore()
        }
        bindingClass.buttonRight.setOnClickListener {
            logicModel.summRight()
            updateScore()
        }
        bindingClass.buttonRestart.setOnClickListener {
            restartGame()
        }

    }

    fun updateScore() {
        var finalScore = 0
        for (i in logicModel.grid.indices) {
            for (j in logicModel.grid[i].indices) {
                val cell = logicModel.grid[i][j]
                finalScore += cell
                val rowView = bindingClass.mainContainer.children.toList()[i] as? TableRow
                val cellView = rowView?.children?.toList()?.get(j) as? TextView
                cellView?.text = cell.toString()
                cellView?.setBackgroundColor(logicModel.selectColor(cell))
            }
        }
        bindingClass.score.text = finalScore.toString()
    }
    fun restartGame() {
        logicModel.grid = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        logicModel.spawnNumber()
        logicModel.spawnNumber()
        updateScore()
    }
}