package com.kyh.musicplayer.data.dataSource

import android.app.Application
import com.kyh.musicplayer.data.AppDatabase
import com.kyh.musicplayer.data.`var`.Code
import com.kyh.musicplayer.data.dao.MusicDao
import com.kyh.musicplayer.data.dao.MusicPlayListDao
import com.kyh.musicplayer.data.dao.PlayListDao
import com.kyh.musicplayer.data.entity.Music
import com.kyh.musicplayer.data.entity.PlayList

class Repository(application: Application) {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)!!

    val musicDao: MusicDao = appDatabase.musicDao()
    val playListDao: PlayListDao = appDatabase.playListDao()
    val musicPlayListDao: MusicPlayListDao = appDatabase.musicPlayListDao()

    private val localDataSource: LocalDataSourceImpl = LocalDataSourceImpl.getInstance(
        musicDao,
        playListDao,
        musicPlayListDao
    )!!
    private val remoteDataSource: RemoteDataSourceImpl = RemoteDataSourceImpl.getInstance(
        musicDao,
        playListDao,
        musicPlayListDao
    )!!

    companion object {
        private var INSTANCE: Repository? = null

        fun getInstance(application: Application): Repository? {
            if (INSTANCE == null) {
                synchronized(Repository::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Repository(application)
                    }
                }
            }

            return INSTANCE
        }
    }

    fun selPlayListComboData(calllback: DataSource.SelPlayListComboDataCallback) {
        localDataSource.selPlayListComboData(calllback)
    }

    fun insMusicData(musicList: MutableList<Music>, callback: DataSource.InsMusicDataCallback) {
        localDataSource.insMusicData(musicList, callback)
    }

    fun uptMusicGenre(selMusicList: ArrayList<Music>, callback: DataSource.UptMusicGenreCallback) {
        localDataSource.uptMusicGenre(selMusicList, callback)
    }

    fun insPlayList(playlistName: String, callback: DataSource.InsPlayListCallback) {
        localDataSource.insPlayList(playlistName, callback)
    }

    fun insMusicPlayList(selPlayList: String, musicList: ArrayList<Music>, callback: DataSource.InsMusicPlayListCallback) {
        localDataSource.insMusicPlayList(selPlayList, musicList, callback)
    }

    fun delPlayList(playlistName: String, callback: DataSource.DelPlayListCallback) {
        localDataSource.delPlayList(playlistName, callback)
    }
}