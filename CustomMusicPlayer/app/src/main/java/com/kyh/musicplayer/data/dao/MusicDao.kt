package com.kyh.musicplayer.data.dao

import androidx.paging.DataSource
import androidx.room.*
import com.kyh.musicplayer.data.entity.Music

@Dao
abstract class MusicDao {
    @Query("SELECT * FROM music")
    abstract fun selectAll(): DataSource.Factory<Int, Music>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAll(musicList: MutableList<Music>)

    @Query("DELETE FROM music")
    abstract fun deleteAll()

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun updMusicGenre(vararg items: Music)
}