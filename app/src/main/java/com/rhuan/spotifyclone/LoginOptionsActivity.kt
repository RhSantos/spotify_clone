package com.rhuan.spotifyclone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rhuan.spotifyclone.databinding.ActivityLoginOptionsBinding


class LoginOptionsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginOptionsBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        if(mAuth.currentUser != null){
            startActivity(Intent(this, HomePageActivity().javaClass))
            finish()
        }

        bindView()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        val btnEntrar = binding.entrar
        val btnEntrarCelular = binding.entrarCelular
        val btnEntrarGoogle = binding.entrarGoogle
        val btnEntrarFacebook = binding.entrarFacebook
        val textCriarConta = binding.criarConta

        btnEntrar.setOnClickListener(View.OnClickListener {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, LoginActivity().javaClass))
            }, 1000);
        })

        btnEntrarCelular.setOnClickListener(View.OnClickListener {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePageActivity().javaClass))
                finish()
            }, 1000);
        })

        btnEntrarGoogle.setOnClickListener(View.OnClickListener {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePageActivity().javaClass))
                finish()
            }, 1000);
        })

        btnEntrarFacebook.setOnClickListener(View.OnClickListener {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePageActivity().javaClass))
                finish()
            }, 3000);
        })

        textCriarConta.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,RegisterActivity().javaClass))
        })

    }

    private fun bindView(){
        binding = ActivityLoginOptionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}