package com.rhuan.spotifyclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rhuan.spotifyclone.databinding.ActivityHomePageBinding
import com.rhuan.spotifyclone.databinding.ActivityLoginBinding

class HomePage : AppCompatActivity() {

    lateinit var binding:ActivityHomePageBinding
    lateinit var rv: RecyclerView
    lateinit var rv2: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        rv = binding.rvTendencias

        rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv.adapter = MusicAdapter(fakeMusics(this))

        rv2 = binding.rvTop10Artistas

        rv2.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv2.adapter = MusicAdapter(fakeMusics(this))
    }

    private fun bindView(){
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}