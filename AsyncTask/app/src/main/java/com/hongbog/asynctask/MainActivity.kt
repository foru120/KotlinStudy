package com.hongbog.asynctask

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonDownload.setOnClickListener {
            val asyncTask = CustomAsynctask(this)

            asyncTask?.execute(editUrl.text.toString())
        }
    }

    class CustomAsynctask constructor(activity: MainActivity) : AsyncTask<String, Void, Bitmap?>() {
        private var actRef: WeakReference<MainActivity> = WeakReference(activity)

        override fun doInBackground(vararg params: String?): Bitmap? {
            var urlString = params[0]!!
            try {
                val url = URL(urlString)
                val stream = url.openStream()
                return BitmapFactory.decodeStream(stream)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)

            var act = this.actRef.get();

            if (result != null) {
                act?.imagePreview?.setImageBitmap(result)
            } else {
                Toast.makeText(act, "다운로드 오류", Toast.LENGTH_SHORT).show()
            }
        }
    }
}