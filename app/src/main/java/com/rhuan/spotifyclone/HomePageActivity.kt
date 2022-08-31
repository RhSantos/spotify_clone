package com.rhuan.spotifyclone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rhuan.spotifyclone.databinding.ActivityHomePageBinding
import java.util.*
import kotlin.collections.HashMap


class HomePageActivity : AppCompatActivity() {

    lateinit var binding:ActivityHomePageBinding
    lateinit var rvTrends: RecyclerView
    lateinit var rvTop: RecyclerView
    private lateinit var mAuth: FirebaseAuth
    var musicList:MutableList<Music> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        mAuth = FirebaseAuth.getInstance()

        val fab = binding.fab
        val profile = binding.profile

        rvTrends = binding.rvTendencias

        rvTrends.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        musicList.addAll(fakeMusics(this))

        rvTrends.adapter = MusicAdapter(musicList,1)

        rvTop = binding.rvTop10Artistas

        rvTop.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvTop.adapter = MusicAdapter(musicList,2)

        profile.setOnClickListener(View.OnClickListener {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, ProfileActivity().javaClass))
            }, 1000);
        })

        fab.setOnClickListener(View.OnClickListener {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, SongRegisterActivity().javaClass))
            }, 1000);
        })
    }

    override fun onResume() {
        super.onResume()
        if(mAuth.currentUser == null){
            finish()
        }

        var musicList:MutableList<Music> = mutableListOf()

        FirebaseDatabase
            .getInstance()
            .getReference("Musics")
            .get()
            .addOnCompleteListener{
                val map = it.result.value as HashMap<String,Object>
                val list = mutableListOf<String>()
                map.forEach { list.add(Gson().toJson(it.value)) }
                list.forEach { musicList.add(Gson().fromJson(it,Music::class.java)) }
                rvTrends.adapter = MusicAdapter(musicList,1)
                rvTop.adapter = MusicAdapter(musicList,2)
            }
    }

    private fun bindView(){
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}