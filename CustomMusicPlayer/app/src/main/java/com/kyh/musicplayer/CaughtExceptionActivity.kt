package com.kyh.musicplayer

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kyh.musicplayer.music.MusicActivity
import kotlin.system.exitProcess

class CaughtExceptionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        var errorMessage: String? = null
        if (intent.extras != null) {
            errorMessage = intent.extras!!.getString("errorMessage")
        }

        MaterialAlertDialogBuilder(this).apply {
            setTitle(getString(R.string.error_dialog_title))
            setMessage(errorMessage)
            setPositiveButton(getString(R.string.confirm_txt)) { _: DialogInterface, i: Int ->
                finishAffinity()
                val intent = Intent(this@CaughtExceptionActivity, MusicActivity::class.java)
                startActivity(intent)
                exitProcess(0)
            }
        }.show()
    }
}