package com.kyh.musicplayer.data.roomData

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo

data class PlayListData(
    @ColumnInfo(name = "no")
    var no: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "has_nothing")
    val hasNothing: Boolean,
    @ColumnInfo(name = "has_hiphop")
    val hasHiphop: Boolean,
    @ColumnInfo(name = "has_rock_n_roll")
    val hasRockNRoll: Boolean,
    @ColumnInfo(name = "has_edm")
    val hasEdm: Boolean,
    @ColumnInfo(name = "has_ballade")
    val hasBallade: Boolean,
    @ColumnInfo(name = "has_classic")
    val hasClassic: Boolean,
    @ColumnInfo(name = "music_cnt")
    val musicCnt: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(no)
        parcel.writeString(name)
        parcel.writeByte(if (hasNothing) 1 else 0)
        parcel.writeByte(if (hasHiphop) 1 else 0)
        parcel.writeByte(if (hasRockNRoll) 1 else 0)
        parcel.writeByte(if (hasEdm) 1 else 0)
        parcel.writeByte(if (hasBallade) 1 else 0)
        parcel.writeByte(if (hasClassic) 1 else 0)
        parcel.writeInt(musicCnt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayListData> {
        override fun createFromParcel(parcel: Parcel): PlayListData {
            return PlayListData(parcel)
        }

        override fun newArray(size: Int): Array<PlayListData?> {
            return arrayOfNulls(size)
        }
    }

}