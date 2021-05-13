package com.kyh.musicplayer.util.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.kyh.musicplayer.R
import com.kyh.musicplayer.databinding.DialogLoadingBinding

class LoadingDialog(context: Context): BaseDialog(context) {
    private val binding: DialogLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_loading, null, false)

    init {
        setContentView(binding.root)
    }
}