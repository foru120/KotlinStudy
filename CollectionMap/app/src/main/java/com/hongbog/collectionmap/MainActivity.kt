package com.hongbog.collectionmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 맵 생성
        var map = mutableMapOf<String, String>()

        // 2. 값 넣기
        map.put("키1", "값1")
        map.put("키2", "값2")
        map.put("키3", "값3")

        // 3. 값 사용
        var variable = map.get("키2")
        Log.d("Collection", "키2의 값은 ${variable}입니다.")

        // 4. 값 수정
        map.put("키2", "두 번째 값 수정")
        Log.d("Collection", "키2의 값은 ${map.get("키2")}입니다.")

        // 5. 값 삭제
        map.remove("키2")
        Log.d("Collection", "키2의 값은 ${map.get("키2")}입니다.")
    }
}