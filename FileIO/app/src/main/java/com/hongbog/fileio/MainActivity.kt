package com.hongbog.fileio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fileUtil = FileUtil()
        fileUtil.writeTextFile("$filesDir", "filename.txt", "글의 내용")
        fileUtil.readTextFile("${filesDir}/filename.txt")
    }
}