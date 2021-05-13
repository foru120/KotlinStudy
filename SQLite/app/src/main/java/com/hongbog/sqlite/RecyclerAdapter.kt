package com.hongbog.sqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper: SqliteHelper? = null
    var listData = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view, this)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData[position]
        holder.setMemo(memo)
    }

    override fun getItemCount(): Int = listData.size

    class Holder(itemView: View, adapter: RecyclerAdapter) : RecyclerView.ViewHolder(itemView) {
        var mMemo: Memo? = null
        val itemViewRef = WeakReference<View>(itemView)
        val adapterRef = WeakReference<RecyclerAdapter>(adapter)

        init {
            val view = itemViewRef.get()
            val recyclerAdapter = adapterRef.get()

            view?.buttonDelete?.setOnClickListener {
                recyclerAdapter?.helper?.deleteMemo(mMemo!!)
                recyclerAdapter?.listData?.remove(mMemo)
                recyclerAdapter?.notifyDataSetChanged()
            }
        }

        fun setMemo(memo: Memo) {
            val view = itemViewRef.get()

            view?.textNo?.text = "${memo.no}"
            view?.textContent?.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            view?.textDatetime?.text = "${sdf.format(memo.datetime)}"

            this.mMemo = memo
        }
    }
}