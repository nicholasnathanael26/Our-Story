package com.example.ourstory.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ourstory.R
import com.example.ourstory.db.Favorite
import com.example.ourstory.db.FavoriteHelper
import kotlinx.android.synthetic.main.item_list_favorites.view.*

class FavoriteListAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteListAdapter.NoteViewHolder>() {

    private lateinit var favoriteHelper: FavoriteHelper

    var listFavorite = ArrayList<Favorite>()
        set(listFavorite){
            if(listFavorite.size > 0 ){
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }

    fun removeItem(position: Int){
        this.listFavorite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavorite.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteListAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_favorites, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteListAdapter.NoteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = this.listFavorite.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(favorite: Favorite){
            with(itemView){
                Glide.with(itemView.context)
                    .load(favorite.cover)
                    .apply(RequestOptions().override(350, 550))
                    .into(img_item_photo)
                penulis.text = favorite.penulis
                title_book.text = favorite.title

                btn_remove.setOnClickListener{
                    favoriteHelper = FavoriteHelper.getInstance(itemView.context)
                    val result = favoriteHelper.deleteById(favorite.id.toString()).toLong()
                    if (result > 0){
                        Toast.makeText(itemView.context,"Favorite Telah Dihapuss!!!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(itemView.context,"Favorite Gagal Dihapuss!!!", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

}