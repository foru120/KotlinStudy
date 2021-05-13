package com.kyh.musicplayer.data.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "playlist"
)
data class PlayList constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "no")
    var no: Int,
    @ColumnInfo(name = "name")
    var name: String
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(no)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayList> {
        override fun createFromParcel(parcel: Parcel): PlayList {
            return PlayList(parcel)
        }

        override fun newArray(size: Int): Array<PlayList?> {
            return arrayOfNulls(size)
        }
    }
}