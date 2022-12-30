package com.example.ourstory

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ourstory.R
import com.example.ourstory.adapter.ListPartAdapter
import com.example.ourstory.db.DatabaseContract
import com.example.ourstory.db.FavoriteHelper
import com.example.ourstory.model.Book
import com.example.ourstory.model.BookPart
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book.*


class BookFragment : Fragment() {

    private lateinit var favoriteHelper: FavoriteHelper

    companion object{
        var query = ""
        var title_book = ""
        var image = ""
        var numPart = 0
        var description = ""
        var penulis = ""
        var bookPart = ArrayList<BookPart>()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorite.setOnClickListener {
            favorite_btn()
        }

        share.setOnClickListener{
            share_btn()
        }

        favoriteHelper = FavoriteHelper.getInstance(requireContext())
//        setActionBarTitle(title_book)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Picasso.get().load(image).into(img_book)

        tv_title.text = title_book
        tv_part.text = numPart.toString()
        tv_description.text = description
        tv_penulis.text = penulis
        part.layoutManager = LinearLayoutManager(context)
        val listPartAdapter = ListPartAdapter(bookPart)
        part.adapter = listPartAdapter
    }

    private fun favorite_btn() {
        val cover = image
        val title = title_book
        val penulis = penulis

        val values = ContentValues()
        values.put(DatabaseContract.favoritecolumns.COVER, cover)
        values.put(DatabaseContract.favoritecolumns.TITLE, title)
        values.put(DatabaseContract.favoritecolumns.PENULIS, penulis)

        val result = favoriteHelper.insert(values)

        if(result > 0 ){
            Toast.makeText(requireContext(),"Berhasil Menambahkan ke favorite!!!",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(),"Gagal Menambahkan ke favorite!!!",Toast.LENGTH_SHORT).show()
        }

    }

    private fun share_btn() {
        Toast.makeText(context,"SHARE!!!", Toast.LENGTH_SHORT).show()
    }


}