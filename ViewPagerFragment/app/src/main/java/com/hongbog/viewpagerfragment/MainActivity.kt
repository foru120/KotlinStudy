package com.hongbog.viewpagerfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentList = listOf<Fragment>(FragmentA(), FragmentB(), FragmentC(), FragmentD())
        val tabTitle = listOf<String>("A", "B", "C", "D")

        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        viewPager.adapter = adapter

        TabLayoutMediator(tablayout, viewPager) { tab, position ->
            tab.text = tabTitle.get(position)
        }.attach()
    }
}