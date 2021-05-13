package com.hongbog.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.hongbog.room.entity.RoomMemo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var helper: RoomHelper? = null
        helper = Room.databaseBuilder(this, RoomHelper::class.java, "room_memo")
                .allowMainThreadQueries()
                .build()
        val adapter = RecyclerAdapter()
        adapter.helper = helper
        adapter.listData = helper?.roomMemoDao()?.getAll() ?: mutableListOf()
        recyclerMemo.adapter = adapter
        recyclerMemo.layoutManager = LinearLayoutManager(this)

        buttonSave.setOnClickListener {
            if (editMemo.text.toString().isNotEmpty()) {
                val memo = RoomMemo(editMemo.text.toString(), System.currentTimeMillis())
                helper?.roomMemoDao().insert(memo)
                adapter.listData.clear()

                adapter.listData.addAll(helper?.roomMemoDao().getAll() ?: mutableListOf())
                adapter.notifyDataSetChanged()
                editMemo.setText("")
            }
        }
    }
}