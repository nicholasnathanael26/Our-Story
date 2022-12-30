package com.example.ourstory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ourstory.BookFragment
import com.example.ourstory.R
import com.example.ourstory.databinding.ItemCardviewBookBinding
import com.example.ourstory.model.Book

class CardViewDiscoverAdapter(private val listBook: ArrayList<Book>, private val manager: FragmentTransaction) : RecyclerView.Adapter<CardViewDiscoverAdapter.CardViewViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): CardViewDiscoverAdapter.CardViewViewHolder {
        val binding = ItemCardviewBookBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return CardViewViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CardViewDiscoverAdapter.CardViewViewHolder,
        position: Int
    ) {
        holder.bind(listBook[position])
    }

    override fun getItemCount(): Int = listBook.size


    inner class CardViewViewHolder(private val binding:ItemCardviewBookBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (book: Book){
            with(binding){
                Glide.with(itemView.context)
                    .load(book.image)
                    .apply(RequestOptions().override(350, 550))
                    .into(bookImgId)
                bookTitle.text = book.title
                rating.text = book.rating.toString()

                val itemBook = Book(
                        book.id,
                        book.title,
                        book.image,
                        book.description,
                        book.penulis,
                        book.numPart,
                        book.rating,
                        book.part
                )

                itemView.setOnClickListener{

                    BookFragment.title_book = book.title.toString()
                    BookFragment.image = book.image.toString()
                    BookFragment.numPart = book.numPart
                    BookFragment.description = book.description.toString()
                    BookFragment.penulis = book.penulis.toString()
                    BookFragment.bookPart = book.part
                    manager.replace(R.id.fragment_container, BookFragment(), BookFragment::class.java.simpleName).addToBackStack(BookFragment::class.java.simpleName).commit()
                }
            }
        }
    }
}