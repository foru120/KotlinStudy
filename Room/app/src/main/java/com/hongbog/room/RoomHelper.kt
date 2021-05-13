package com.hongbog.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hongbog.room.dao.RoomMemoDao
import com.hongbog.room.entity.RoomMemo

@Database(entities = arrayOf(RoomMemo::class), version = 1, exportSchema = false)
abstract class RoomHelper : RoomDatabase() {
    abstract fun roomMemoDao(): RoomMemoDao
}