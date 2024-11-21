package com.example.lab7

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    context: Context,
    data: List<Item>,
    private val layout: Int
) : ArrayAdapter<Item>(context, layout, data) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        // Inflate the layout for the item, reuse if already exists
        val view = convertView ?: View.inflate(parent.context, layout, null)

        // Get the data item at the given position
        val item = getItem(position) ?: return view

        // Set the image to the ImageView
        val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
        imgPhoto.setImageResource(item.photo)

        // Set the message to the TextView
        val tvMsg = view.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = if (layout == R.layout.adapter_vertical) {
            item.name // Vertical layout shows only the name
        } else {
            "${item.name}: ${item.price}元" // Horizontal layout shows name and price
        }

        // Return the view for this item
        return view
    }
}
