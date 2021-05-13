package com.kyh.musicplayer.music

import android.graphics.ImageDecoder
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kyh.musicplayer.R
import com.kyh.musicplayer.data.`var`.Code
import com.kyh.musicplayer.data.entity.Music
import com.kyh.musicplayer.data.entity.MusicPlayList
import com.kyh.musicplayer.databinding.ItemMusicBinding
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat

class MusicListAdapter() : PagedListAdapter<Music, MusicListAdapter.MusicViewHolder>(
    DIFF_CALLBACK
) {
    val selectedItems: SparseBooleanArray = SparseBooleanArray(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(
            this,
            binding
        )
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val item: Music? = getItem(position)

        if (item != null) {
            holder.itemView.isSelected = selectedItems.get(position, false)
            holder.bindTo(item)
        }
    }

    fun getSelectedItem(): ArrayList<Music> {
        val selectedMusicList = ArrayList<Music>()
        for (i in 0 until itemCount) {
            if (selectedItems[i]) selectedMusicList.add(getItem(i)!!)
        }
        return selectedMusicList
    }

    fun selectAllItem() {
        for (i in 0 until itemCount) {
            selectedItems.put(i, true)
        }
        notifyDataSetChanged()
    }

    fun clearSelectedItem() {
        for (i in 0 until selectedItems.size()) {
            val pos: Int = selectedItems.keyAt(i)
            selectedItems.put(pos, false)
        }
        notifyDataSetChanged()
    }

    class MusicViewHolder(musicListAdapter: MusicListAdapter, itemMusicBinding: ItemMusicBinding): RecyclerView.ViewHolder(itemMusicBinding.root) {
        private val ref: WeakReference<MusicListAdapter> = WeakReference(musicListAdapter)
        private val adt: MusicListAdapter? = ref.get()
        private val binding = itemMusicBinding

        fun bindTo(music: Music) {
            try {
                // WRITE_EXTERNAL_STORAGE 권한 필요
                val albumBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) ImageDecoder.decodeBitmap(ImageDecoder.createSource(binding.root.context.contentResolver, music.getAlbumUri()))
                else MediaStore.Images.Media.getBitmap(binding.root.context.contentResolver, music.getAlbumUri())

                binding.albumImg.setImageBitmap(albumBitmap)
            } catch (e: Exception) {
                binding.albumImg.setImageResource(R.drawable.no_image)
            }
            binding.titleTxt.text = music.title
            binding.artistTxt.text = music.artist
            val duration = SimpleDateFormat("mm:ss").format(music.duration)
            binding.durationTxt.text = duration
            binding.genreChip.text = if (music.genreName.isNullOrEmpty()) Code.Genre.NOTHING.toString() else music.genreName

            itemView.setOnClickListener {
                val pos: Int = bindingAdapterPosition

                if (adt?.selectedItems!!.get(pos, false)) {
                    adt.selectedItems.put(pos, false)
                } else {
                    adt.selectedItems.put(pos, true)
                }

                adt.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Music>() {
                override fun areItemsTheSame(oldItem: Music, newItem: Music) = oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Music, newItem: Music) = oldItem == newItem
        }
    }
}