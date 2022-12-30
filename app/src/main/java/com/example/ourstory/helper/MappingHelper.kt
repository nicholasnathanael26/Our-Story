package com.example.ourstory.helper

import android.database.Cursor
import com.example.ourstory.db.DatabaseContract
import com.example.ourstory.db.Favorite

object MappingHelper {
    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<Favorite>{
        val favoriteList = ArrayList<Favorite>()

        favoriteCursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.favoritecolumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.favoritecolumns.TITLE))
                val cover = getString(getColumnIndexOrThrow(DatabaseContract.favoritecolumns.COVER))
                val penulis = getString(getColumnIndexOrThrow(DatabaseContract.favoritecolumns.PENULIS))
                favoriteList.add(Favorite(id, cover, title, penulis))
            }
        }
        return favoriteList
    }
}