package com.example.ourstory.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "dbfavorite"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE ${DatabaseContract.favoritecolumns.TABLE_NAME}" + "(${DatabaseContract.favoritecolumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${DatabaseContract.favoritecolumns.COVER} TEXT NOT NULL,"+
                "${DatabaseContract.favoritecolumns.TITLE} TEXT NOT NULL,"+
                "${DatabaseContract.favoritecolumns.PENULIS} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.favoritecolumns.TABLE_NAME}")
        onCreate(db)
    }
}