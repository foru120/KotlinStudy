package com.hongbog.controlflow1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ball = 4
        if (ball > 3) {
            Log.d("ControlFlow", "4볼로 출루합니다.")
        } else {
            Log.d("ControlFlow", "타석에서 다음 타구를 기다립니다.")
        }
    }
}