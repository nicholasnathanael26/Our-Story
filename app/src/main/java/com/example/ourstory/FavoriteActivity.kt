package com.example.ourstory

import android.bluetooth.BluetoothAdapter.EXTRA_STATE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ourstory.adapter.FavoriteListAdapter
import com.example.ourstory.db.Favorite
import com.example.ourstory.db.FavoriteHelper
import com.example.ourstory.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.item_list_favorites.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteListAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = "Favorite"

        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)
        adapter = FavoriteListAdapter(this)
        rv_favorite.adapter = adapter

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        if(savedInstanceState == null){
            loadFavorite()
        }else{
            val list = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)
            if(list != null){
                adapter.listFavorite = list
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        favoriteHelper.close()
//    }

    private fun loadFavorite(){
        GlobalScope.launch (Dispatchers.Main){
            progressBar.visibility = View.VISIBLE
            val deferredFavorite = async (Dispatchers.IO){
                val cursor = favoriteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressBar.visibility = View.INVISIBLE
            val favorite = deferredFavorite.await()
            if(favorite.size > 0 ){
                adapter.listFavorite = favorite
            }else{
                adapter.listFavorite = ArrayList()
                Snackbar.make(rv_favorite,"Belum Ada List Favorite", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorite)
    }
}