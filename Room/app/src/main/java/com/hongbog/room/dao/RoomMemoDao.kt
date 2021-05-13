package com.hongbog.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.hongbog.room.entity.RoomMemo

@Dao
interface RoomMemoDao {
    @Query("select * from orm_memo")
    fun getAll(): MutableList<RoomMemo>

    @Insert(onConflict = REPLACE)
    fun insert(memo: RoomMemo)

    @Delete
    fun delete(memo: RoomMemo)
}