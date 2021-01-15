package com.hongbog.controlflow2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var a = 1
        var b = 2
        var c = 3

        // 1. if문 두 번 사용하기
        if (a < b) {
            Log.d("ControlFlow", "1: a는 b보다 작습니다.")
        }
        if (a < c) {
            Log.d("ControlFlow", "1: a는 c보다 작습니다.")
        }

        // 2. else if문 사용하기
        if (a < b) {
            Log.d("ControlFlow", "2: a는 b보다 작습니다.")
        } else if (a < c) {
            Log.d("ControlFlow", "2: a는 c보다 작습니다.")
        }
    }
}