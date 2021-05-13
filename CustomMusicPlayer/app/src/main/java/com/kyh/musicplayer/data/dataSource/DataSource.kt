package com.kyh.musicplayer.data.dataSource

import com.kyh.musicplayer.data.entity.Music
import com.kyh.musicplayer.data.entity.PlayList

interface DataSource {

    fun selPlayListComboData(callback: SelPlayListComboDataCallback)
    interface SelPlayListComboDataCallback {
        fun onSucceedSelPlayListComboData(playListCombo: ArrayList<PlayList>)
        fun onFailSelPlayListComboData(errorMessage: String?)
    }

    fun insMusicData(musicList: MutableList<Music>, callback: InsMusicDataCallback)
    interface InsMusicDataCallback {
        fun onSucceedInsMusicData()
        fun onFailInsMusicData(errorMessage: String?)
    }

    interface PlayListRecyclerViewEventCallback {
        fun onItemLongClick(playlistName: String)
    }

    fun uptMusicGenre(selMusicList: ArrayList<Music>, callback: UptMusicGenreCallback)
    interface UptMusicGenreCallback {
        fun onSucceedUptMusicGenre()
        fun onFailUptMusicGenre(errorMessage: String?)
    }

    fun insPlayList(playlistName: String, callback: InsPlayListCallback)
    interface InsPlayListCallback {
        fun onSucceedInsPlayList()
        fun onFailInsPlayList(errorMessage: String?)
    }

    fun insMusicPlayList(selPlayList: String, musicList: ArrayList<Music>, callback: InsMusicPlayListCallback)
    interface InsMusicPlayListCallback {
        fun onSucceedInsMusicPlayList()
        fun onFailInsMusicPlayList(errorMessage: String?)
    }

    fun delPlayList(playlistName: String, callback: DelPlayListCallback)
    interface DelPlayListCallback {
        fun onSucceedDelPlayList()
        fun onFailDelPlayList(errorMessage: String?)
    }
}