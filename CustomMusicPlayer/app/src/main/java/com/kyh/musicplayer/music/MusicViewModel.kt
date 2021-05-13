package com.kyh.musicplayer.music

import android.app.Application
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.kyh.musicplayer.data.`var`.Code
import com.kyh.musicplayer.data.dataSource.DataSource
import com.kyh.musicplayer.data.dataSource.Repository
import com.kyh.musicplayer.data.entity.Music
import com.kyh.musicplayer.data.entity.PlayList
import com.kyh.musicplayer.data.roomData.PlayListData
import com.orhanobut.logger.Logger

class MusicViewModel(application: Application): AndroidViewModel(application),
    DataSource.SelPlayListComboDataCallback,
    DataSource.InsMusicDataCallback,
    DataSource.UptMusicGenreCallback,
    DataSource.InsPlayListCallback,
    DataSource.InsMusicPlayListCallback,
    DataSource.DelPlayListCallback {

    /**
     * @내용 : 공통 변수
     * @작성자 : 길용현
     **/
    private val app: Application = application
    private val repository: Repository = Repository.getInstance(app)!!
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * @내용 : MusicListFragment 에서 사용하는 변수
     * @작성자 : 길용현
     **/
    private var musicDataSource: androidx.paging.DataSource.Factory<Int, Music> = repository.musicDao.selectAll()
    val musicList = musicDataSource.toLiveData(pageSize = 10)
    val playListCombo: MutableLiveData<ArrayList<PlayList>> = MutableLiveData()

    val isClearMusicData: MutableLiveData<Boolean> = MutableLiveData()
    val isGenreEdit: MutableLiveData<Boolean> = MutableLiveData()
    val isSelect: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * @내용 : PlayListFragment 에서 사용하는 변수
     * @작성자 : 길용현
     **/
    private var playListDataSource: androidx.paging.DataSource.Factory<Int, PlayListData> = repository.playListDao.selectAll()
    val playList = playListDataSource.toLiveData(pageSize = 10)

    /**
     * @내용 : [MusicListFragment] 플레이리스트 ComboBox 데이터를 가져오는 함수
     * @수정 :
     * @버젼 : 0.0.0
     * @최초작성일 : 2021-05-13 오전 10:31
     * @작성자 : 길용현
     **/
    fun selPlayListComboData() {
        repository.selPlayListComboData(this)
    }

    override fun onSucceedSelPlayListComboData(playListCombo: ArrayList<PlayList>) {
        this.playListCombo.value = playListCombo
    }

    override fun onFailSelPlayListComboData(errorMessage: String?) {
        this.errorMessage.value = errorMessage
    }

    /**
     * @내용 : [MusicListFragment] 동기화 버튼 클릭시 현재 기기에 있는 MP3 파일 목록을 가져와 저장하는 함수
     * @수정 :
     * @버젼 : 0.0.0
     * @최초작성일 : 2021-05-13 오전 10:32
     * @작성자 : 길용현
     **/
    fun insMusicData() {
        try {
            /**
             * MP3 파일 추가시 외부 탐색기를 이용해 복사 붙여넣기로 내장메모리에 저장하는 경우 MediaStore 에서 인식이 안됨.
             * 핸드폰 자체 탐색기인 내파일 앱에서 해당 음악 폴더를 아무곳에서 잘래내기 붙여넣기로 옮기면 인식됨.
             */
            this.isLoading.value = true

            val musicListUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val musicProj = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION
            )
            val cursor = app.applicationContext.contentResolver.query(
                musicListUri,
                musicProj,
                null,
                null,
                MediaStore.Audio.Media.TITLE + " ASC"
            )

            val musicList = mutableListOf<Music>()
            cursor.use { c ->
                while (c?.moveToNext() == true) {
                    val music = Music(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getLong(3),
                        Code.Genre.NOTHING.toString(),
                        c.getLong(4)
                    )
                    musicList.add(music)
                }
            }
            repository.insMusicData(musicList, this)
        } catch (e: Exception) {
            this.errorMessage.value = e.message
        }
    }

    override fun onSucceedInsMusicData() {
        this.isLoading.value = false
    }

    override fun onFailInsMusicData(errorMessage: String?) {
        this.errorMessage.value = errorMessage
    }

    fun setIsGenreEdit() {
        this.isGenreEdit.value = !(this.isGenreEdit.value != null && this.isGenreEdit.value!!)
    }

    fun setIsSelect() {
        this.isSelect.value = !(this.isSelect.value != null && this.isSelect.value!!)
    }

    /**
     * @내용 : [MusicListFragment] 선택된 음악에 대한 장르를 변경하는 함수
     * @수정 :
     * @버젼 : 0.0.0
     * @최초작성일 : 2021-05-13 오전 10:32
     * @작성자 : 길용현
     **/
    fun uptMusicGenre(selMusicList: ArrayList<Music>) {
        repository.uptMusicGenre(selMusicList, this)
    }

    override fun onSucceedUptMusicGenre() {
        this.isLoading.value = false
        this.isClearMusicData.value = true
        setIsGenreEdit()
    }

    override fun onFailUptMusicGenre(errorMessage: String?) {
        this.errorMessage.value = errorMessage
    }

    /**
     * @내용 : [MusicListFragment] 선택된 음악파일을 특정 플레이리스트에 추가하는 함수
     * @수정 :
     * @버젼 : 0.0.0
     * @최초작성일 : 2021-05-13 오전 11:14
     * @작성자 : 길용현
     **/
    fun insMusicPlayList(selPlayList: String, musicList: ArrayList<Music>) {
        this.isLoading.value = true
        repository.insMusicPlayList(selPlayList, musicList, this)
    }

    override fun onSucceedInsMusicPlayList() {
        this.isLoading.value = false
        this.isClearMusicData.value = true
    }

    override fun onFailInsMusicPlayList(errorMessage: String?) {
        this.isLoading.value = false
        this.errorMessage.value = errorMessage
    }

    /**
     * @내용 : [PlayListFragment] 플레이리스트를 추가하는 함수
     * @수정 :
     * @버젼 : 0.0.0
     * @최초작성일 : 2021-05-13 오전 10:33
     * @작성자 : 길용현
     **/
    fun insPlayList(playlistName: String) {
        this.isLoading.value = true
        repository.insPlayList(playlistName, this)
    }

    override fun onSucceedInsPlayList() {
        selPlayListComboData()
        this.isLoading.value = false
    }

    override fun onFailInsPlayList(errorMessage: String?) {
        this.isLoading.value = false
        this.errorMessage.value = errorMessage
    }

    fun delPlayList(playlistName: String) {
        this.isLoading.value = true
        repository.delPlayList(playlistName, this)
    }

    override fun onSucceedDelPlayList() {
        this.isLoading.value = false
    }

    override fun onFailDelPlayList(errorMessage: String?) {
        this.isLoading.value = false
        this.errorMessage.value = errorMessage
    }
}