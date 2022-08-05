package com.rhuan.spotifyclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        setContentView(R.layout.activity_home_page)
    }
}