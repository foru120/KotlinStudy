package com.kyh.musicplayer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kyh.musicplayer.data.dao.MusicDao
import com.kyh.musicplayer.data.dao.MusicPlayListDao
import com.kyh.musicplayer.data.dao.PlayListDao
import com.kyh.musicplayer.data.entity.Music
import com.kyh.musicplayer.data.entity.MusicPlayList
import com.kyh.musicplayer.data.entity.PlayList

@Database(entities = [Music::class, PlayList::class, MusicPlayList::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun musicDao(): MusicDao
    abstract fun playListDao(): PlayListDao
    abstract fun musicPlayListDao(): MusicPlayListDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "database")
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }

            return INSTANCE
        }
    }
}