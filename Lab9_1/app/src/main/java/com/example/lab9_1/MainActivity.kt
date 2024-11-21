package com.example.lab9_1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Variables to track the progress of the rabbit and the turtle
    private var progressRabbit = 0
    private var progressTurtle = 0

    // UI components
    private lateinit var btnStart: Button
    private lateinit var sbRabbit: SeekBar
    private lateinit var sbTurtle: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge layout
        setContentView(R.layout.activity_main)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind UI components
        btnStart = findViewById(R.id.btnStart)
        sbRabbit = findViewById(R.id.sbRabbit)
        sbTurtle = findViewById(R.id.sbTurtle)

        // Set click listener for the start button
        btnStart.setOnClickListener {
            btnStart.isEnabled = false // Disable the button during the race

            // Reset progress
            progressRabbit = 0
            progressTurtle = 0
            sbRabbit.progress = 0
            sbTurtle.progress = 0

            // Start the race
            runRabbit()
            runTurtle()
        }
    }

    // Helper method to display a Toast message
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    // Handler to update UI based on messages from threads
    private val handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            1 -> {
                sbRabbit.progress = progressRabbit
                if (progressRabbit >= 100 && progressTurtle < 100) {
                    showToast("兔子勝利") // Rabbit wins
                    btnStart.isEnabled = true
                }
            }
            2 -> {
                sbTurtle.progress = progressTurtle
                if (progressTurtle >= 100 && progressRabbit < 100) {
                    showToast("烏龜勝利") // Turtle wins
                    btnStart.isEnabled = true
                }
            }
        }
        true
    }

    // Simulate rabbit's movement in a thread
    private fun runRabbit() {
        Thread {
            val sleepProbability = arrayOf(true, true, false) // Rabbit has a 2/3 chance of sleeping
            while (progressRabbit < 100 && progressTurtle < 100) {
                try {
                    Thread.sleep(100) // Delay for 0.1 seconds
                    if (sleepProbability.random()) {
                        Thread.sleep(300) // Rabbit sleeps for 0.3 seconds
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressRabbit += 3 // Rabbit moves 3 steps at a time
                handler.sendMessage(Message().apply { what = 1 }) // Send progress update
            }
        }.start()
    }

    // Simulate turtle's movement in a thread
    private fun runTurtle() {
        Thread {
            while (progressTurtle < 100 && progressRabbit < 100) {
                try {
                    Thread.sleep(100) // Delay for 0.1 seconds
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressTurtle += 1 // Turtle moves 1 step at a time
                handler.sendMessage(Message().apply { what = 2 }) // Send progress update
            }
        }.start()
    }
}
