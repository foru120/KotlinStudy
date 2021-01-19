package com.hongbog.widgetsprogressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread(start=true) {
            Thread.sleep(3000)
            runOnUiThread {
                showProgress(false)
            }
        }
    }

    fun showProgress(show: Boolean) {
        if (show) {
            progressLayout.visibility = View.VISIBLE
        } else {
            progressLayout.visibility = View.GONE
        }
    }
}