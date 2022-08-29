package com.rhuan.spotifyclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rhuan.spotifyclone.databinding.ActivitySongRegisterBinding

class SongRegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySongRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        val voltar = binding.voltar
        voltar.setOnClickListener(View.OnClickListener { finish() })

    }

    private fun bindView(){
        binding = ActivitySongRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}