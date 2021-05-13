package com.kyh.musicplayer.music

import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.SparseBooleanArray
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.kyh.musicplayer.R
import com.kyh.musicplayer.data.`var`.Code
import com.kyh.musicplayer.data.roomData.PlayListData
import com.kyh.musicplayer.databinding.ItemPlaylistBinding
import java.lang.ref.WeakReference

class PlaytListAdapter constructor(
    val playListRecyclerViewEventHandler: PlayListFragment.PlayListRecyclerViewEventHandler
) : PagedListAdapter<PlayListData, PlaytListAdapter.PlayListViewHolder>(
    DIFF_CALLBACK
){
    val selectedItems: SparseBooleanArray = SparseBooleanArray(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val binding = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayListViewHolder(
            this,
            binding
        )
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        val item: PlayListData? = getItem(position)

        if (item != null) {
            holder.itemView.isSelected = selectedItems.get(position, false)
            holder.bindTo(item)
        }
    }

    class PlayListViewHolder(playtListAdapter: PlaytListAdapter, itemPlaylistBinding: ItemPlaylistBinding) : RecyclerView.ViewHolder(itemPlaylistBinding.root) {
        private val ref: WeakReference<PlaytListAdapter> = WeakReference(playtListAdapter)
        private val adt: PlaytListAdapter? = ref.get()
        private val binding = itemPlaylistBinding

        fun bindTo(playListData: PlayListData) {
            binding.playlistName.text = playListData.name
            binding.musicCnt.text = TextUtils.concat(playListData.musicCnt.toString(), " 개")

            binding.chipGroup.removeAllViews()

            if (playListData.hasBallade) binding.chipGroup.addView(createChip(Code.Genre.BALLADE.toString()))
            if (playListData.hasClassic) binding.chipGroup.addView(createChip(Code.Genre.CLASSIC.toString()))
            if (playListData.hasEdm) binding.chipGroup.addView(createChip(Code.Genre.EDM.toString()))
            if (playListData.hasHiphop) binding.chipGroup.addView(createChip(Code.Genre.HIPHOP.toString()))
            if (playListData.hasRockNRoll) binding.chipGroup.addView(createChip(Code.Genre.ROCK_N_ROLL.toString()))
            if (playListData.hasNothing) binding.chipGroup.addView(createChip(Code.Genre.NOTHING.toString()))

            binding.playlistView.setOnLongClickListener {
                adt?.playListRecyclerViewEventHandler?.onItemLongClick(binding.playlistName.text as String)
                return@setOnLongClickListener true
            }
        }

        private fun createChip(genre: String): Chip {
            return Chip(binding.root.context, null, R.style.Widget_MaterialComponents_Chip_Choice).apply {
                text = genre
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
                setTextColor(resources.getColor(R.color.medium_grey, null))
                chipBackgroundColor = ColorStateList.valueOf((ContextCompat.getColor(binding.root.context, R.color.dark_grey)))
                isEnabled = false
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<PlayListData>() {
            override fun areItemsTheSame(oldItem: PlayListData, newItem: PlayListData) = oldItem.no == newItem.no

            override fun areContentsTheSame(oldItem: PlayListData, newItem: PlayListData) = oldItem == newItem
        }
    }
}