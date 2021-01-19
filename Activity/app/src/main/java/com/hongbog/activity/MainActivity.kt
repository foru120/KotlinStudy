package com.hongbog.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onClickListener = OnClickListener(this)
        btnStart1.setOnClickListener(onClickListener)
        btnStart2.setOnClickListener(onClickListener)
    }

    class OnClickListener(activity: MainActivity) : View.OnClickListener {
        private var activityRef: WeakReference<MainActivity> = WeakReference(activity)

        override fun onClick(btn: View?) {
            val activity: MainActivity? = activityRef.get()

            if (activity != null) {
                val intent = android.content.Intent(activity, SubActivity::class.java)
                intent.putExtra("from1", "hello Bundle")
                intent.putExtra("from2", 2020)

                when(btn?.id) {
                    R.id.btnStart1 -> activity.startActivityForResult(intent, 98)
                    R.id.btnStart2 -> activity.startActivityForResult(intent, 99)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val message = data?.getStringExtra("returnValue")
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

            when(requestCode) {
                98 -> textView1.text = message
                99 -> textView2.text = message
            }
        }
    }
}