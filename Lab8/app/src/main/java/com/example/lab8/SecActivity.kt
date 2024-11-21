package com.example.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge UI
        setContentView(R.layout.activity_sec)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Declare UI components and retrieve them using findViewById
        val edName = findViewById<EditText>(R.id.edName)
        val edPhone = findViewById<EditText>(R.id.edPhone)
        val btnSend = findViewById<Button>(R.id.btnSend)

        // Set up the button's click listener to get user input
        btnSend.setOnClickListener {
            // Check if the name and phone fields are filled
            when {
                edName.text.isEmpty() -> showToast("請輸入姓名") // Show a toast if name is empty
                edPhone.text.isEmpty() -> showToast("請輸入電話") // Show a toast if phone is empty
                else -> {
                    // Bundle the name and phone inputs
                    val bundle = Bundle().apply {
                        putString("name", edName.text.toString())
                        putString("phone", edPhone.text.toString())
                    }
                    // Use setResult() to return the contact data
                    setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
                    finish() // Close the activity
                }
            }
        }
    }

    // Helper method to show Toast messages
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
