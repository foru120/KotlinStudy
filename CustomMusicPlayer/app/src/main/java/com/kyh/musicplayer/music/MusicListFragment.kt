package com.kyh.musicplayer.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kyh.musicplayer.R
import com.kyh.musicplayer.data.`var`.Code
import com.kyh.musicplayer.data.dataSource.DataSource
import com.kyh.musicplayer.data.entity.Music
import com.kyh.musicplayer.data.entity.PlayList
import com.kyh.musicplayer.databinding.FragmentMusicListBinding
import java.util.logging.Logger

class MusicListFragment: Fragment() {
    private lateinit var binding: FragmentMusicListBinding
    private val viewModel: MusicViewModel by activityViewModels()

    private lateinit var musicListView: RecyclerView
    private lateinit var musicListAdapter: PagedListAdapter<Music, MusicListAdapter.MusicViewHolder>

    private lateinit var playListComboAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_music_list, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModel

        setUpView()
        setUpObserver()

        return binding.root
    }

    private fun setUpView() {
        musicListView = binding.musicListView
        musicListAdapter = MusicListAdapter()
        musicListView.adapter = musicListAdapter
        musicListView.layoutManager = LinearLayoutManager(requireContext())

        binding.genreEditBtn.setOnClickListener {
            if (viewModel.isGenreEdit.value != null && viewModel.isGenreEdit.value!!) {
                viewModel.isLoading.value = true
                val selMusicList = (musicListAdapter as MusicListAdapter).getSelectedItem()
                val selGenreItem = binding.genreChipsView.binding?.genreChipGrp?.checkedChipId
                if (selMusicList.size == 0 || selGenreItem == View.NO_ID) {
                    viewModel.isLoading.value = false
                    viewModel.setIsGenreEdit()
                    return@setOnClickListener
                }
                var selGenreName: Code.Genre? = null
                when(selGenreItem) {
                    binding.genreChipsView.binding?.balladeChips?.id -> selGenreName = Code.Genre.BALLADE
                    binding.genreChipsView.binding?.classicChips?.id -> selGenreName = Code.Genre.CLASSIC
                    binding.genreChipsView.binding?.edmChips?.id -> selGenreName = Code.Genre.EDM
                    binding.genreChipsView.binding?.hiphopChips?.id -> selGenreName = Code.Genre.HIPHOP
                    binding.genreChipsView.binding?.rocknrollChips?.id -> selGenreName = Code.Genre.ROCK_N_ROLL
                }

                for (music: Music in selMusicList) {
                    music.genreName = selGenreName.toString()
                }

                viewModel.uptMusicGenre(selMusicList)
            } else {
                viewModel.setIsGenreEdit()
            }
        }

        playListComboAdapter = ArrayAdapter<String>(requireContext(), R.layout.combo_playlist)
        binding.addPlaylistView.binding?.playlistField?.setAdapter(playListComboAdapter)

        binding.addPlaylistView.binding?.addBtn?.setOnClickListener {
            val musicList = (musicListAdapter as MusicListAdapter).getSelectedItem()
            val selPlayList = binding.addPlaylistView.binding?.playlistMenu?.editText?.text.toString()

            if (musicList.size == 0 || selPlayList.isNullOrEmpty()) {
                Toast.makeText(requireContext(), R.string.no_select_item_txt, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.insMusicPlayList(selPlayList, musicList)
            }
        }
    }

    private fun setUpObserver() {
        viewModel.playListCombo.observe(requireActivity(), Observer { playListCombo ->
            if (playListCombo != null) {
                playListComboAdapter.clear()

                val comboList = arrayListOf<String>()
                for (playList: PlayList in playListCombo) {
                    comboList.add(playList.name)
                }
                playListComboAdapter.addAll(comboList)
                playListComboAdapter.notifyDataSetChanged()

                if (playListCombo.size > 0) binding.addPlaylistView.binding?.playlistField?.setText(playListCombo[0].name, false)
            }
        })

        viewModel.musicList?.observe(requireActivity(), Observer { musicList ->
            if (musicList != null) {
                musicListAdapter.submitList(musicList)
            }
        })

        viewModel.isSelect.observe(requireActivity(), Observer { isSelect ->
            if (isSelect) {
                (musicListAdapter as MusicListAdapter).selectAllItem()
            } else {
                (musicListAdapter as MusicListAdapter).clearSelectedItem()
            }
        })

        viewModel.isClearMusicData.observe(requireActivity(), Observer { isClearMusicData ->
            if (isClearMusicData) {
                (musicListAdapter as MusicListAdapter).clearSelectedItem()
            }
        })
    }
}