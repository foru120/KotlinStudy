package com.kyh.musicplayer.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.kyh.musicplayer.R
import com.kyh.musicplayer.data.entity.PlayList
import com.kyh.musicplayer.databinding.DialogAddPlaylistBinding
import com.kyh.musicplayer.music.MusicViewModel

class AddPlayListDialog: DialogFragment() {
    private val viewModel: MusicViewModel by viewModels()
    private lateinit var binding: DialogAddPlaylistBinding

    override fun onResume() {
        super.onResume()
        val windowManager: WindowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val deviceWidth: Int = size.x
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = (deviceWidth * 0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddPlaylistBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUpView()

        return binding.root
    }

    fun setUpView() {
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.confirmBtn.setOnClickListener {
            if (binding.playlistEdt.text.toString().isNullOrEmpty()) {
                Toast.makeText(context?.applicationContext, R.string.no_input_txt, Toast.LENGTH_SHORT).show()
            } else {
                dismiss()
                viewModel.insPlayList(binding.playlistEdt.text.toString())
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}