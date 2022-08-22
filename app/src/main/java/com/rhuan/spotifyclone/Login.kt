package com.rhuan.spotifyclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.rhuan.spotifyclone.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        val btnEntrar = binding.entrar
        val btnEntrarCelular = binding.entrarCelular
        val btnEntrarGoogle = binding.entrarGoogle
        val btnEntrarFacebook = binding.entrarFacebook
        val textCriarConta = binding.criarConta

        btnEntrar.setOnClickListener(View.OnClickListener {
            btnEntrarCelular.setOnClickListener(null)
            btnEntrarGoogle.setOnClickListener(null)
            btnEntrarFacebook.setOnClickListener(null)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePage().javaClass))
                finish()
            }, 1000);
            btnEntrar.setOnClickListener(null)
        })

        btnEntrarCelular.setOnClickListener(View.OnClickListener {
            btnEntrar.setOnClickListener(null)
            btnEntrarGoogle.setOnClickListener(null)
            btnEntrarFacebook.setOnClickListener(null)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePage().javaClass))
                finish()
            }, 1000);
            btnEntrarCelular.setOnClickListener(null)
        })

        btnEntrarGoogle.setOnClickListener(View.OnClickListener {
            btnEntrar.setOnClickListener(null)
            btnEntrarCelular.setOnClickListener(null)
            btnEntrarFacebook.setOnClickListener(null)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePage().javaClass))
                finish()
            }, 1000);
            btnEntrarGoogle.setOnClickListener(null)
        })

        btnEntrarFacebook.setOnClickListener(View.OnClickListener {
            btnEntrar.setOnClickListener(null)
            btnEntrarGoogle.setOnClickListener(null)
            btnEntrarCelular.setOnClickListener(null)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePage().javaClass))
                finish()
            }, 3000);
            btnEntrarFacebook.setOnClickListener(null)
        })

        textCriarConta.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,RegisterActivity().javaClass))
        })

    }

    private fun bindView(){
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}