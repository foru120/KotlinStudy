package com.kyh.musicplayer.music

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kyh.musicplayer.R
import com.kyh.musicplayer.data.dataSource.DataSource
import com.kyh.musicplayer.data.roomData.PlayListData
import com.kyh.musicplayer.databinding.FragmentPlaylistBinding
import com.kyh.musicplayer.dialog.AddPlayListDialog
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_playlist.*
import java.lang.ref.WeakReference

class PlayListFragment: Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private val viewModel: MusicViewModel by activityViewModels()

    private lateinit var playlistView: RecyclerView
    private lateinit var adapter: PagedListAdapter<PlayListData, PlaytListAdapter.PlayListViewHolder>

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private var isFabOpen: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_playlist, container, false
        )
        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModel

        setUpView()
        setUpObserver()

        return binding.root
    }

    private fun setUpView() {
        playlistView = binding.playlistView
        adapter = PlaytListAdapter(PlayListRecyclerViewEventHandler(this))
        playlistView.adapter = adapter
        playlistView.layoutManager = LinearLayoutManager(requireContext())

        fabOpen = AnimationUtils.loadAnimation(requireContext().applicationContext, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(requireContext().applicationContext, R.anim.fab_close)

        val fabOnClickListener = FabOnClickListener(this)
        binding.mainFab.setOnClickListener(fabOnClickListener)
        binding.addPlaylistFab.setOnClickListener(fabOnClickListener)
    }

    private fun setUpObserver() {
        viewModel.playList.observe(requireActivity(), Observer { playList ->
            if (playList != null) {
                adapter.submitList(playList)
            }
        })
    }

    class FabOnClickListener constructor(
        fragment: PlayListFragment
    ): View.OnClickListener {
        private val fragRef: WeakReference<PlayListFragment> = WeakReference(fragment)

        override fun onClick(view: View?) {
            val frag = fragRef.get()

            when(view?.id) {
                R.id.main_fab -> {
                    if (frag?.isFabOpen!!) {
                        frag.binding.addPlaylistFab.startAnimation(frag.fabClose)
                        frag.binding.screenView.visibility = View.GONE
                        frag.binding.addPlaylistFab.visibility = View.GONE
                        frag.binding.addPlaylistTxt.visibility = View.GONE
                        frag.isFabOpen = false
                    } else {
                        frag.binding.addPlaylistFab.startAnimation(frag.fabOpen)
                        frag.binding.screenView.visibility = View.VISIBLE
                        frag.binding.addPlaylistFab.visibility = View.VISIBLE
                        frag.binding.addPlaylistTxt.visibility = View.VISIBLE
                        frag.isFabOpen = true
                    }
                }
                R.id.add_playlist_fab -> frag?.showDialog()
            }
        }
    }

    fun showDialog() {
        val fragmentManager = requireActivity().supportFragmentManager
        val newFragment = AddPlayListDialog()
        newFragment.isCancelable = false
        newFragment.show(fragmentManager, "addPlayListDialog")
    }

    class PlayListRecyclerViewEventHandler constructor(
        playListFragment: PlayListFragment
    ): DataSource.PlayListRecyclerViewEventCallback {
        private val ref: WeakReference<PlayListFragment> = WeakReference(playListFragment)

        override fun onItemLongClick(playlistName: String) {
            val frag: PlayListFragment? = ref.get()

            MaterialAlertDialogBuilder(frag?.requireContext()!!).apply {
                setTitle(R.string.del_playlist_txt)
                setMessage(TextUtils.concat("[", playlistName, "]"))
                setNegativeButton(frag.requireActivity().resources.getString(R.string.cancel_txt)) { dialog, which ->

                }
                setPositiveButton(frag.requireActivity().resources.getString(R.string.confirm_txt)) { dialog, which ->
                    frag.viewModel.delPlayList(playlistName)
                }
            }.show()
        }
    }
}