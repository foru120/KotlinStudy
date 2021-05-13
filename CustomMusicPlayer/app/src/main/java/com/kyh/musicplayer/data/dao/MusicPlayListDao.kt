package com.kyh.musicplayer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyh.musicplayer.data.entity.Music
import com.kyh.musicplayer.data.entity.MusicPlayList

@Dao
abstract class MusicPlayListDao {
    @Query("SELECT `no` FROM playlist WHERE name = :name")
    abstract fun selPlayListNo(name: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAll(musicPlayList: ArrayList<MusicPlayList>)

    open fun insMusicPlayList(selPlayList: String, musicList: ArrayList<Music>) {
        val selPlayListNo = selPlayListNo(selPlayList)
        val musicPlayList = ArrayList<MusicPlayList>()
        for (music in musicList) {
            musicPlayList.add(MusicPlayList(music.id, selPlayListNo))
        }
        insertAll(musicPlayList)
    }
}