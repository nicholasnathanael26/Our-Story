package com.example.ourstory.model

import android.os.Parcel
import android.os.Parcelable

data class Book(
    var id: String? = null,
    var title: String? = null,
    var image: String? = null,
    var description: String? = null,
    var penulis: String? = null,
    var numPart: Int = 0,
    var rating: Int = 0,
    val part: ArrayList<BookPart>,
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readArrayList(null) as ArrayList<BookPart>
    ){
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeString(penulis)
        parcel.writeInt(numPart)
        parcel.writeInt(rating)
        parcel.writeArray(arrayOf(part))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}