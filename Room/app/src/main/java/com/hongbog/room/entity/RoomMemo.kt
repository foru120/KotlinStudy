package com.hongbog.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orm_memo")
class RoomMemo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var content: String = ""

    @ColumnInfo(name = "date")
    var datetime: Long = 0

    constructor(content: String, datetime: Long) {
        this.content = content
        this.datetime = datetime
    }
}