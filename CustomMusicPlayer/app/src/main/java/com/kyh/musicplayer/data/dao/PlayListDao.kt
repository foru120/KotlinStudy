package com.kyh.musicplayer.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyh.musicplayer.data.entity.PlayList
import com.kyh.musicplayer.data.roomData.PlayListData

@Dao
abstract class PlayListDao {
    @Query("select a.no,\n" +
            "       a.name,\n" +
            "       case when nothing_cnt > 0 then 1 else 0 end has_nothing,\n" +
            "       case when hiphop_cnt > 0 then 1 else 0 end has_hiphop,\n" +
            "       case when rock_n_roll_cnt > 0 then 1 else 0 end has_rock_n_roll,\n" +
            "       case when edm_cnt > 0 then 1 else 0 end has_edm,\n" +
            "       case when ballade_cnt > 0 then 1 else 0 end has_ballade,\n" +
            "       case when classic_cnt > 0 then 1 else 0 end has_classic,\n" +
            "       ifnull(nothing_cnt, 0) + ifnull(hiphop_cnt, 0) + ifnull(rock_n_roll_cnt, 0) + ifnull(edm_cnt, 0) + ifnull(ballade_cnt, 0) + ifnull(classic_cnt, 0) music_cnt\n" +
            "  from (select a.no,\n" +
            "\t\t\t   a.name,\n" +
            "\t\t\t   sum(nothing_cnt) nothing_cnt,\n" +
            "\t\t\t   sum(hiphop_cnt) hiphop_cnt,\n" +
            "\t\t\t   sum(rock_n_roll_cnt) rock_n_roll_cnt,\n" +
            "\t\t\t   sum(edm_cnt) edm_cnt,\n" +
            "\t\t\t   sum(ballade_cnt) ballade_cnt,\n" +
            "\t\t\t   sum(classic_cnt) classic_cnt\n" +
            "\t\t  from (select a.no,\n" +
            "\t\t\t\t\t   a.name,\n" +
            "\t\t\t\t\t   case a.genre_name when 'NOTHING' then a.cnt end nothing_cnt,\n" +
            "\t\t\t\t\t   case a.genre_name when 'HIPHOP' then a.cnt end hiphop_cnt,\n" +
            "\t\t\t\t\t   case a.genre_name when 'ROCK_N_ROLL' then a.cnt end rock_n_roll_cnt,\n" +
            "\t\t\t\t\t   case a.genre_name when 'EDM' then a.cnt end edm_cnt,\n" +
            "\t\t\t\t\t   case a.genre_name when 'BALLADE' then a.cnt end ballade_cnt,\n" +
            "\t\t\t\t\t   case a.genre_name when 'CLASSIC' then a.cnt end classic_cnt\n" +
            "\t\t\t\t  from (select a.no,\n" +
            "\t\t\t\t\t\t\t   a.name,\n" +
            "\t\t\t\t\t\t\t   c.genre_name,\n" +
            "\t\t\t\t\t\t\t   count(*) cnt\n" +
            "\t\t\t\t\t\t  from playlist a left outer join music_playlist b\n" +
            "\t\t\t\t\t\t\ton (b.playlist_no = a.no)\n" +
            "\t\t\t\t\t\t\t   left outer join music c\n" +
            "\t\t\t\t\t\t\ton (c.id = b.music_id)\n" +
            "\t\t\t\t\t\t group by a.no,\n" +
            "\t\t\t\t\t\t\t\t  a.name,\n" +
            "\t\t\t\t\t\t\t\t  c.genre_name) a) a\n" +
            "\t\t group by a.no,\n" +
            "\t\t\t\t  a.name) a")
    abstract fun selectAll(): DataSource.Factory<Int, PlayListData>

    @Query("INSERT INTO playlist(name) VALUES (:playlistName)")
    abstract fun insPlayList(playlistName: String)

    @Query("SELECT * FROM playlist")
    abstract fun selPlayListCombo(): List<PlayList>

    @Query("DELETE FROM playlist WHERE name = :playlistName")
    abstract fun delPlayList(playlistName: String)
}