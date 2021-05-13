package com.kyh.musicplayer.music

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.kyh.musicplayer.*
import com.kyh.musicplayer.databinding.ActivityMainBinding
import com.kyh.musicplayer.util.dialog.LoadingDialog

class MusicActivity : BaseActivity() {
    private val viewModel: MusicViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var loadingDialog: LoadingDialog? = null

    private val musicListFragment: Fragment =
        MusicListFragment()
    private val playListFragment: Fragment =
        PlayListFragment()
    private val settingFragment: Fragment =
        SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermission()
    }

    private fun startProcess() {
        setUpBinding()
        setUpView()
        setUpObserver()
    }

    private fun checkPermission() {
        val permissions = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 99)
                return
            }
        }
        startProcess()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 99) {
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "권한 요청을 승인해야지만 앱을 실행할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            startProcess()
        }
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        binding.viewModel = this.viewModel
    }

    private fun setUpView() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.add(R.id.main_view, musicListFragment)
        ft.add(R.id.main_view, playListFragment)
        ft.add(R.id.main_view, settingFragment).commit()

        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

            when(item.itemId) {
                R.id.music_list_tab -> {
                    ft.show(musicListFragment)
                    ft.hide(playListFragment)
                    ft.hide(settingFragment).commit()

                    viewModel.selPlayListComboData()
                    true
                }
                R.id.play_list_tab -> {
                    ft.hide(musicListFragment)
                    ft.show(playListFragment)
                    ft.hide(settingFragment).commit()
                    true
                }
                R.id.setting_tab -> {
                    ft.hide(musicListFragment)
                    ft.hide(playListFragment)
                    ft.show(settingFragment).commit()
                    true
                }
                else -> false
            }
        }

        binding.bottomNav.selectedItemId = R.id.music_list_tab
    }

    private fun setUpObserver() {
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            val intent: Intent = Intent(this, CaughtExceptionActivity::class.java).apply {
                putExtra("errorMessage", errorMessage)
            }
            startActivity(intent)
        })

        viewModel.isLoading.observe(this, Observer { isInitData ->
            if (loadingDialog != null && loadingDialog!!.isShowing) loadingDialog!!.dismiss()

            if (isInitData) {
                loadingDialog = LoadingDialog(this)
                loadingDialog!!.show()
            }
        })
    }
}