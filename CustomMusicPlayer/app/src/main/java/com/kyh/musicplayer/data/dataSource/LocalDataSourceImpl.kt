package com.kyh.musicplayer.data.dataSource

import com.kyh.musicplayer.data.dao.MusicDao
import com.kyh.musicplayer.data.dao.MusicPlayListDao
import com.kyh.musicplayer.data.dao.PlayListDao
import com.kyh.musicplayer.data.entity.Music
import com.kyh.musicplayer.data.entity.PlayList
import com.kyh.musicplayer.util.thread.AppExecutors

class LocalDataSourceImpl constructor(
    val musicDao: MusicDao,
    val playListDao: PlayListDao,
    val musicPlayListDao: MusicPlayListDao
): DataSource {
    private val appExecutor: AppExecutors = AppExecutors.getInstance()

    companion object {
        private var INSTANCE: LocalDataSourceImpl? = null

        fun getInstance(musicDao: MusicDao,
                        playListDao: PlayListDao,
                        musicPlayListDao: MusicPlayListDao
        ): LocalDataSourceImpl? {
            if (INSTANCE == null) {
                synchronized(LocalDataSourceImpl::class) {
                    if (INSTANCE == null) {
                        INSTANCE = LocalDataSourceImpl(musicDao, playListDao, musicPlayListDao)
                    }
                }
            }

            return INSTANCE
        }
    }

    override fun selPlayListComboData(callback: DataSource.SelPlayListComboDataCallback) {
        appExecutor.diskThread.execute {
            try {
                val playListComboData = playListDao.selPlayListCombo() as ArrayList

                appExecutor.mainThread.execute {
                    callback.onSucceedSelPlayListComboData(playListComboData)
                }
            } catch (e: Exception) {
                appExecutor.mainThread.execute {
                    callback.onFailSelPlayListComboData(e.message)
                }
            }
        }
    }

    override fun insMusicData(
        musicList: MutableList<Music>,
        callback: DataSource.InsMusicDataCallback
    ) {
        appExecutor.diskThread.execute {
            try {
                musicDao.insertAll(musicList)

                appExecutor.mainThread.execute {
                    callback.onSucceedInsMusicData()
                }
            } catch (e: Exception) {
                appExecutor.mainThread.execute {
                    callback.onFailInsMusicData(e.message)
                }
            }
        }
    }

    override fun uptMusicGenre(
        selMusicList: ArrayList<Music>,
        callback: DataSource.UptMusicGenreCallback
    ) {
        appExecutor.diskThread.execute {
            try {
                musicDao.updMusicGenre(*selMusicList.toTypedArray())

                appExecutor.mainThread.execute { callback.onSucceedUptMusicGenre() }
            } catch (e: Exception) {
                appExecutor.mainThread.execute { callback.onFailUptMusicGenre(e.message) }
            }
        }
    }

    override fun insPlayList(playlistName: String, callback: DataSource.InsPlayListCallback) {
        appExecutor.diskThread.execute {
            try {
                playListDao.insPlayList(playlistName)

                appExecutor.mainThread.execute { callback.onSucceedInsPlayList() }
            } catch (e: Exception) {
                appExecutor.mainThread.execute { callback.onFailInsPlayList(e.message) }
            }
        }
    }

    override fun insMusicPlayList(
        selPlayList: String,
        musicList: ArrayList<Music>,
        callback: DataSource.InsMusicPlayListCallback
    ) {
        appExecutor.diskThread.execute {
            try {
                musicPlayListDao.insMusicPlayList(selPlayList, musicList)

                appExecutor.mainThread.execute { callback.onSucceedInsMusicPlayList() }
            } catch (e: Exception) {
                appExecutor.mainThread.execute { callback.onFailInsMusicPlayList(e.message) }
            }
        }
    }

    override fun delPlayList(playlistName: String, callback: DataSource.DelPlayListCallback) {
        appExecutor.diskThread.execute {
            try {
                playListDao.delPlayList(playlistName)

                appExecutor.mainThread.execute { callback.onSucceedDelPlayList() }
            } catch (e: Exception) {
                appExecutor.mainThread.execute { callback.onFailDelPlayList(e.message) }
            }
        }
    }
}