package com.shobeyri.splayer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.shobeyri.splayer.listFragments.AdapterViewPager
import com.shobeyri.splayer.listFragments.fileManager.AdapterFileManager
import com.shobeyri.splayer.listFragments.fileManager.FileManagerFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , FileManagerFragment.CallBackMenu{

    lateinit var adapterViewPager: AdapterViewPager
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(getPermission())
        {
            adapterViewPager = AdapterViewPager(supportFragmentManager)
            pager.adapter = adapterViewPager
        }
    }

    fun getPermission() : Boolean
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true
            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                return false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            adapterViewPager = AdapterViewPager(supportFragmentManager)
            pager.adapter = adapterViewPager
        }
    }

    override fun onBack() {
        onBackPressed()
    }

    override fun onBackPressed() {
        {
            if(pager.currentItem == 5)
            {
                if(!(adapterViewPager.fileFragment as FileManagerFragment).prePage())
                    finishApp()
            }
        }
    }

    fun finishApp()
    {
        finish()
    }
}
