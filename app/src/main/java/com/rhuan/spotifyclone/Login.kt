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

        val btn_entrar = binding.entrar
        val btn_entrar_celular = binding.entrarCelular
        val btn_entrar_google = binding.entrarGoogle
        val btn_entrar_facebook = binding.entrarFacebook

        btn_entrar.setOnClickListener(View.OnClickListener {
            btn_entrar_celular.setOnClickListener(null)
            btn_entrar_google.setOnClickListener(null)
            btn_entrar_facebook.setOnClickListener(null)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePage().javaClass))
                finish()
            }, 3000);
            btn_entrar.setOnClickListener(null)
        })

        btn_entrar_celular.setOnClickListener(View.OnClickListener {
            btn_entrar.setOnClickListener(null)
            btn_entrar_google.setOnClickListener(null)
            btn_entrar_facebook.setOnClickListener(null)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePage().javaClass))
                finish()
            }, 3000);
            btn_entrar_celular.setOnClickListener(null)
        })

        btn_entrar_google.setOnClickListener(View.OnClickListener {
            btn_entrar.setOnClickListener(null)
            btn_entrar_celular.setOnClickListener(null)
            btn_entrar_facebook.setOnClickListener(null)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePage().javaClass))
                finish()
            }, 3000);
            btn_entrar_google.setOnClickListener(null)
        })

        btn_entrar_facebook.setOnClickListener(View.OnClickListener {
            btn_entrar.setOnClickListener(null)
            btn_entrar_google.setOnClickListener(null)
            btn_entrar_celular.setOnClickListener(null)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePage().javaClass))
                finish()
            }, 3000);
            btn_entrar_facebook.setOnClickListener(null)
        })

    }

    private fun bindView(){
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}