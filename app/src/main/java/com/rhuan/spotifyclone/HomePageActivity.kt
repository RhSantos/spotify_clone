package com.rhuan.spotifyclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.rhuan.spotifyclone.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {

    lateinit var binding:ActivityHomePageBinding
    lateinit var rvTrends: RecyclerView
    lateinit var rvTop: RecyclerView
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        mAuth = FirebaseAuth.getInstance()

        val fab = binding.fab
        val profile = binding.profile

        rvTrends = binding.rvTendencias

        rvTrends.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvTrends.adapter = MusicAdapter(fakeMusics(this))

        rvTop = binding.rvTop10Artistas

        rvTop.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvTop.adapter = MusicAdapter(fakeMusics(this))

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
    }

    private fun bindView(){
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}