package com.kyh.musicplayer.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kyh.musicplayer.R
import com.kyh.musicplayer.databinding.CustomGenreChpisViewBinding

class GenreChipsView @JvmOverloads
    constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attributeSet, defStyleAttr) {

    var binding: CustomGenreChpisViewBinding? = null

    init {
        initView()
    }

    private fun initView() {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_genre_chpis_view, this, true)
    }
}