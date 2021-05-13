package com.kyh.musicplayer.util.thread

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    val mainThread: Executor
        get() = MainThreadExecutor()
    val diskThread: Executor
        get() = Executors.newFixedThreadPool(THREAD_COUNT)
    val networkThread: Executor
        get() = Executors.newFixedThreadPool(THREAD_COUNT)
    val taskThread: Executor
        get() = Executors.newFixedThreadPool(THREAD_COUNT)

    private class SingleToneHolder {
        companion object {
            val INSTANCE: AppExecutors = AppExecutors()
        }
    }

    private class MainThreadExecutor: Executor {
        private var mainThreadHandler: Handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        private const val THREAD_COUNT = 3

        fun getInstance(): AppExecutors {
            return SingleToneHolder.INSTANCE
        }
    }
}