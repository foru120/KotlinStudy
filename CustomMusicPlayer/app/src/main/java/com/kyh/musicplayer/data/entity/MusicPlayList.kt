package com.kyh.musicplayer.data.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "music_playlist",
    primaryKeys = [
        "music_id",
        "playlist_no"
    ],
    foreignKeys = [ForeignKey(
        entity = Music::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("music_id")
    ), ForeignKey(
        entity = PlayList::class,
        parentColumns = arrayOf("no"),
        childColumns = arrayOf("playlist_no")
    )]
)
data class MusicPlayList constructor(
    @ColumnInfo(name = "music_id")
    val musicId: String,
    @ColumnInfo(name = "playlist_no")
    val playlistNo: Int
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(musicId)
        parcel.writeInt(playlistNo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicPlayList> {
        override fun createFromParcel(parcel: Parcel): MusicPlayList {
            return MusicPlayList(parcel)
        }

        override fun newArray(size: Int): Array<MusicPlayList?> {
            return arrayOfNulls(size)
        }
    }

}