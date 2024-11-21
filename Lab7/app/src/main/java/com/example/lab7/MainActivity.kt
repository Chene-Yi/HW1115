package com.example.lab7

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display
        setContentView(R.layout.activity_main)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Declare UI components
        val spinner = findViewById<Spinner>(R.id.spinner)
        val listView = findViewById<ListView>(R.id.listView)
        val gridView = findViewById<GridView>(R.id.gridView)

        // Initialize data structures
        val count = ArrayList<String>() // Store purchase quantities
        val item = ArrayList<Item>()    // Store fruit information

        // Define price range
        val priceRange = IntRange(10, 100)

        // Load images from resources
        val array = resources.obtainTypedArray(R.array.image_list)
        for (index in 0 until array.length()) {
            val photo = array.getResourceId(index, 0) // Fruit image ID
            val name = "水果${index + 1}"              // Fruit name
            val price = priceRange.random()           // Random price

            // Add data to lists
            count.add("${index + 1}個")
            item.add(Item(photo, name, price))
        }
        array.recycle() // Release resources

        // Setup the spinner with an ArrayAdapter
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            count
        )

        // Configure the GridView
        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, item, R.layout.adapter_vertical)

        // Configure the ListView
        listView.adapter = MyAdapter(this, item, R.layout.adapter_horizontal)
    }
}
