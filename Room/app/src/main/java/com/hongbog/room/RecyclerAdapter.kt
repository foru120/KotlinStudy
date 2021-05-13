package com.hongbog.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hongbog.room.entity.RoomMemo
import kotlinx.android.synthetic.main.item_recycler.view.*
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper: RoomHelper? = null
    var listData = mutableListOf<RoomMemo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view, this)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val RoomMemo = listData[position]
        holder.setRoomMemo(RoomMemo)
    }

    override fun getItemCount(): Int = listData.size

    class Holder(itemView: View, adapter: RecyclerAdapter) : RecyclerView.ViewHolder(itemView) {
        var mRoomMemo: RoomMemo? = null
        val itemViewRef = WeakReference<View>(itemView)
        val adapterRef = WeakReference<RecyclerAdapter>(adapter)

        init {
            val view = itemViewRef.get()
            val recyclerAdapter = adapterRef.get()

            view?.buttonDelete?.setOnClickListener {
                recyclerAdapter?.helper?.roomMemoDao()?.delete(mRoomMemo!!)
                recyclerAdapter?.listData?.remove(mRoomMemo)
                recyclerAdapter?.notifyDataSetChanged()
            }
        }

        fun setRoomMemo(RoomMemo: RoomMemo) {
            val view = itemViewRef.get()

            view?.textNo?.text = "${RoomMemo.no}"
            view?.textContent?.text = RoomMemo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            view?.textDatetime?.text = "${sdf.format(RoomMemo.datetime)}"

            this.mRoomMemo = RoomMemo
        }
    }
}