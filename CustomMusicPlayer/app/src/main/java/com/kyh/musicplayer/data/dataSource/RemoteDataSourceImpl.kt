package com.kyh.musicplayer.data.dataSource

import com.kyh.musicplayer.data.dao.MusicDao
import com.kyh.musicplayer.data.dao.MusicPlayListDao
import com.kyh.musicplayer.data.dao.PlayListDao
import com.kyh.musicplayer.data.entity.Music
import com.kyh.musicplayer.util.thread.AppExecutors

class RemoteDataSourceImpl constructor(
    val musicDao: MusicDao,
    val playListDao: PlayListDao,
    val musicPlayListDao: MusicPlayListDao
): DataSource {
    private val appExecutor: AppExecutors = AppExecutors.getInstance()

    companion object {
        private var INSTANCE: RemoteDataSourceImpl? = null

        fun getInstance(musicDao: MusicDao,
                        playListDao: PlayListDao,
                        musicPlayListDao: MusicPlayListDao
        ): RemoteDataSourceImpl? {
            if (INSTANCE == null) {
                synchronized(RemoteDataSourceImpl::class) {
                    if (INSTANCE == null) {
                        INSTANCE = RemoteDataSourceImpl(musicDao, playListDao, musicPlayListDao)
                    }
                }
            }

            return INSTANCE
        }
    }

    override fun selPlayListComboData(callback: DataSource.SelPlayListComboDataCallback) {
        TODO("Not yet implemented")
    }

    override fun insMusicData(musicList: MutableList<Music>, callback: DataSource.InsMusicDataCallback) {
        TODO("Not yet implemented")
    }

    override fun uptMusicGenre(
        selMusicList: ArrayList<Music>,
        callback: DataSource.UptMusicGenreCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun insPlayList(playlistName: String, callback: DataSource.InsPlayListCallback) {
        TODO("Not yet implemented")
    }

    override fun insMusicPlayList(
        selPlayList: String,
        musicList: ArrayList<Music>,
        callback: DataSource.InsMusicPlayListCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun delPlayList(playlistName: String, callback: DataSource.DelPlayListCallback) {
        TODO("Not yet implemented")
    }
}