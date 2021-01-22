package com.hongbog.viewpagerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = listOf<String>("A", "B", "C", "D")
        val data: List<String> = listOf<String>("뷰A", "뷰B", "뷰C", "뷰D")
        val adapter = CustomPagerAdapter(data)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = title.get(position)
        }.attach()
    }
}