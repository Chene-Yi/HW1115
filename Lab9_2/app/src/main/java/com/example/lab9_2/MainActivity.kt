package com.example.lab9_2

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    // Variables to bind UI components
    private lateinit var btnCalculate: Button
    private lateinit var edHeight: EditText
    private lateinit var edWeight: EditText
    private lateinit var edAge: EditText
    private lateinit var tvWeightResult: TextView
    private lateinit var tvFatResult: TextView
    private lateinit var tvBmiResult: TextView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var llProgress: LinearLayout
    private lateinit var btnBoy: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind UI components
        btnCalculate = findViewById(R.id.btnCalculate)
        edHeight = findViewById(R.id.edHeight)
        edWeight = findViewById(R.id.edWeight)
        edAge = findViewById(R.id.edAge)
        tvWeightResult = findViewById(R.id.tvWeightResult)
        tvFatResult = findViewById(R.id.tvFatResult)
        tvBmiResult = findViewById(R.id.tvBmiResult)
        tvProgress = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)
        llProgress = findViewById(R.id.llProgress)
        btnBoy = findViewById(R.id.btnBoy)

        // Set click listener for the Calculate button
        btnCalculate.setOnClickListener {
            when {
                edHeight.text.isEmpty() -> showToast("請輸入身高")
                edWeight.text.isEmpty() -> showToast("請輸入體重")
                edAge.text.isEmpty() -> showToast("請輸入年齡")
                else -> runThread() // Execute the calculation in a thread
            }
        }
    }

    // Helper method to show Toast messages
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    // Simulate the calculation process in a thread
    private fun runThread() {
        // Reset UI elements
        tvWeightResult.text = "標準體重\n無"
        tvFatResult.text = "體脂肪\n無"
        tvBmiResult.text = "BMI\n無"
        progressBar.progress = 0
        tvProgress.text = "0%"
        llProgress.visibility = View.VISIBLE

        Thread {
            var progress = 0
            // Simulate progress over 5 seconds
            while (progress < 100) {
                try {
                    Thread.sleep(50) // Delay 50ms for each step
                } catch (ignored: InterruptedException) {
                }
                progress++
                runOnUiThread {
                    progressBar.progress = progress
                    tvProgress.text = "$progress%"
                }
            }

            // Perform calculations
            val height = edHeight.text.toString().toDouble()
            val weight = edWeight.text.toString().toDouble()
            val age = edAge.text.toString().toDouble()
            val bmi = weight / ((height / 100).pow(2))

            // Calculate standard weight and body fat percentage
            val (standWeight, bodyFat) = if (btnBoy.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }

            // Update UI on the main thread
            runOnUiThread {
                llProgress.visibility = View.GONE
                tvWeightResult.text = "標準體重 \n${String.format("%.2f", standWeight)}"
                tvFatResult.text = "體脂肪 \n${String.format("%.2f", bodyFat)}"
                tvBmiResult.text = "BMI \n${String.format("%.2f", bmi)}"
            }
        }.start()
    }
}
