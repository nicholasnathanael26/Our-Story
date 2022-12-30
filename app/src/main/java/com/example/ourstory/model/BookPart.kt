package com.example.ourstory.model

import android.os.Parcel
import android.os.Parcelable

data class BookPart (
        var id: Int = 0,
        var title: String? = null,
        var url: String? = null,
        var rating: Int = 0
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeInt(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookPart> {
        override fun createFromParcel(parcel: Parcel): BookPart {
            return BookPart(parcel)
        }

        override fun newArray(size: Int): Array<BookPart?> {
            return arrayOfNulls(size)
        }
    }
}