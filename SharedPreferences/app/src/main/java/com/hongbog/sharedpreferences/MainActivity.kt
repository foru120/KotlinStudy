package com.hongbog.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shared = PreferenceManager.getDefaultSharedPreferences(this)

        val checkboxValue = shared.getBoolean("key_add_shortcut", false)
        val switchValue = shared.getBoolean("key_switch_on", false)
        val name = shared.getString("key_edit_name", "")
        val selected = shared.getString("key_set_item", "")
    }
}