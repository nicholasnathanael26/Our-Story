package com.example.ourstory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ourstory.databinding.ItemListPartBinding
import com.example.ourstory.model.BookPart

class ListPartAdapter(private val listPart: ArrayList<BookPart>) : RecyclerView.Adapter<ListPartAdapter.ListViewViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i : Int
    ): ListViewViewHolder {
        val binding = ItemListPartBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return ListViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListPartAdapter.ListViewViewHolder, position: Int) {
        holder.bind(listPart[position])
    }

    override fun getItemCount(): Int = listPart.size

    inner class ListViewViewHolder(private val binding: ItemListPartBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(bookPart: BookPart){
            with(binding){
                tvTitle.text = bookPart.title
                tvRating.text = bookPart.rating.toString()

                itemView.setOnClickListener{
                    Toast.makeText(itemView.context, "${bookPart.title}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}