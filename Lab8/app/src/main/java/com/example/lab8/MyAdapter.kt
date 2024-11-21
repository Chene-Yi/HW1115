package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val data: ArrayList<Contact> // List of contact data
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // ViewHolder class to hold and bind views
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // Store references to UI components
        private val tvName: TextView = v.findViewById(R.id.tvName)
        private val tvPhone: TextView = v.findViewById(R.id.tvPhone)
        private val imgDelete: ImageView = v.findViewById(R.id.imgDelete)

        // Bind the data to the views and set up click listener
        fun bind(item: Contact, clickListener: (Contact) -> Unit) {
            tvName.text = item.name
            tvPhone.text = item.phone
            // Set up the delete button's click listener
            imgDelete.setOnClickListener {
                // Invoke the click listener, passing the item to delete
                clickListener.invoke(item)
            }
        }
    }

    // Create the ViewHolder and inflate the layout
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_row, viewGroup, false) // Inflate the row layout
        return ViewHolder(v)
    }

    // Return the size of the dataset
    override fun getItemCount() = data.size

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position]) { item ->
            // Remove the item from the dataset
            data.remove(item)
            // Notify the adapter that the data has changed
            notifyDataSetChanged()
        }
    }
}
