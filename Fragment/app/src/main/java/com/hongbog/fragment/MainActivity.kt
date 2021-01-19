package com.hongbog.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment()
    }

    fun goDetail() {
        val detailFragment = DetailFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, detailFragment)
        transaction.addToBackStack("detail")
        transaction.commit()
    }

    fun goBack() {
        onBackPressed()
    }

    fun setFragment() {
        val listFragment = ListFragment()
        var bundle = Bundle()
        bundle.putString("key1", "List Fragment")
        bundle.putInt("key2", 20170727)
        listFragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, listFragment)
        transaction.commit()
    }
}