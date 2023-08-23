package com.vlados.a2048game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.children
import com.vlados.a2048game.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        updateScore()

        bindingClass.buttonUp.setOnClickListener {
            mainActivityViewModel.summUp()
            updateScore()
        }
        bindingClass.buttonDown.setOnClickListener {
            mainActivityViewModel.summDown()
            updateScore()
        }
        bindingClass.buttonLeft.setOnClickListener {
            mainActivityViewModel.summLeft()
            updateScore()
        }
        bindingClass.buttonRight.setOnClickListener {
            mainActivityViewModel.summRight()
            updateScore()
        }
        bindingClass.buttonRestart.setOnClickListener {
            restartGame()
        }

    }

    fun updateScore() {
        var finalScore = 0
        for (i in mainActivityViewModel.grid.indices) {
            for (j in mainActivityViewModel.grid[i].indices) {
                val cell = mainActivityViewModel.grid[i][j]
                finalScore += cell
                val rowView = bindingClass.mainContainer.children.toList()[i] as? TableRow
                val cellView = rowView?.children?.toList()?.get(j) as? TextView
                cellView?.text = cell.toString()
                cellView?.setBackgroundColor(mainActivityViewModel.selectColor(cell))
            }
        }
        bindingClass.score.text = finalScore.toString()
    }
    fun restartGame() {
        mainActivityViewModel.grid = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        mainActivityViewModel.spawnNumber()
        mainActivityViewModel.spawnNumber()
        updateScore()
    }
}