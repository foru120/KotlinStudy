package com.kyh.musicplayer

import android.app.Application
import android.content.Context
import android.content.Intent
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlin.system.exitProcess

class BasicApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // 로그 출력을 정돈해주는 라이브러리 초기화
        Logger.addLogAdapter(AndroidLogAdapter())

        if (!BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler(this))
        }
    }
}

class AppExceptionHandler constructor(
    private val context: Context
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val intent: Intent = Intent(context, UncaughtExceptionActivity::class.java).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            )
            putExtra("errorMessage", throwable.toString())
        }
        context.startActivity(intent)

        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(10)
    }
}