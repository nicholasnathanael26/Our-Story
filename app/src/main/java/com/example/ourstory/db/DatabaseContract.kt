package com.example.ourstory.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class favoritecolumns : BaseColumns {
        companion object{
            const val TABLE_NAME = "favorite"
            const val _ID = "_id"
            const val COVER = "cover"
            const val TITLE = "title"
            const val PENULIS = "penulis"
        }
    }
}