package com.rhuan.spotifyclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.rhuan.spotifyclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var editEmail:EditText
    private lateinit var editSenha:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        mAuth = FirebaseAuth.getInstance()

        val btnVoltar = binding.voltar
        val btnEntrar = binding.entrar

        editEmail = binding.emailEditText
        editSenha = binding.senhaEditText

        btnVoltar.setOnClickListener(View.OnClickListener { finish() })
        btnEntrar.setOnClickListener(View.OnClickListener { loginUser() })
    }

    private fun loginUser() {
        val email = editEmail.text.toString().trim()
        val senha = editSenha.text.toString().trim()

        mAuth.signInWithEmailAndPassword(email,senha)
            .addOnCompleteListener{
                if(it.isSuccessful) {
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        startActivity(Intent(this, HomePageActivity().javaClass))
                        finish()
                    }, 1000)
                }
            }
    }

    private fun bindView(){
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}