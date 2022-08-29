package com.rhuan.spotifyclone

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rhuan.spotifyclone.databinding.ActivityRegisterBinding
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var mAuth:FirebaseAuth

    private lateinit var editName:EditText
    private lateinit var editUsername:EditText
    private lateinit var editEmail:EditText
    private lateinit var editPassword:EditText
    private lateinit var btnDataNascimento:AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        mAuth = FirebaseAuth.getInstance()

        val textVoltar = binding.voltar
        val btnRegistrar = binding.registrar

        btnDataNascimento = binding.dataNascimento

        editName = binding.name
        editUsername = binding.username
        editEmail = binding.email
        editPassword = binding.password

        textVoltar.setOnClickListener(View.OnClickListener {
            finish()
        })

        btnRegistrar.setOnClickListener(View.OnClickListener {
            registerUser()
        })

        btnDataNascimento.setOnClickListener(View.OnClickListener {
            datePickerShow(btnDataNascimento)
        })

    }

    private fun registerUser(){
        val sdf:SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val name = editName.text.trim()
        val username = editUsername.text.trim()
        val password = editPassword.text.trim()
        val email = editEmail.text.trim()
        val birthDate = btnDataNascimento.text

        when(true){
            name.isEmpty() ->{
                editName.error = "Nome não pode ser vazio"
                editName.requestFocus()
                return
            }

            username.isEmpty() ->{
                editUsername.error = "Nome de Usuário não pode ser vazio"
                editUsername.requestFocus()
                return
            }

            username.toString() != username.toString().lowercase() -> {
                editUsername.error = "Nome de Usuário deve ser inteiramente minúsculo"
                editUsername.requestFocus()
                return
            }

            email.isEmpty() ->{
                editEmail.error = "Email não pode ser vazio"
                editEmail.requestFocus()
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                editEmail.error = "Email Inválido"
                editEmail.requestFocus()
                return
            }

            password.isEmpty() ->{
                editPassword.error = "Senha não pode ser vazia"
                editPassword.requestFocus()
                return
            }

            password.length < 6 ->{
                editPassword.error = "Senha muito curta"
                editPassword.requestFocus()
                return
            }

            birthDate == "Data de Nascimento" ->{
                btnDataNascimento.error = "Data de Nascimento obrigatória"
                return
            }

            else -> Unit
        }

        val actualDate = Calendar.getInstance().time
        val date = sdf.parse(birthDate.toString())

        val verifyDate = actualDate.time - date.time
        val minimumAge = 31536000000 * 13

        if(verifyDate < minimumAge){
            btnDataNascimento.error = "Data de Nascimento Inválida"
            return
        }

        val user:User = User(
            name.toString(),
            username.toString(),
            email.toString(),
            password.toString(),
            date.time
        )

        mAuth.createUserWithEmailAndPassword(user.email,user.password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().uid!!)
                        .setValue(user).addOnCompleteListener{ mAuthTask ->
                            if(mAuthTask.isSuccessful){
                                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                                    startActivity(Intent(this, HomePageActivity().javaClass))
                                    finish()
                                }, 1000)
                            }
                        }
                }
            }
    }

    private fun datePickerShow(btnDataNascimento:AppCompatButton){
        var c: Calendar = Calendar.getInstance()
        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        var mDay = c.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            this,R.style.DatePickerTheme,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                btnDataNascimento.text = "${if(mDay<9)"0${mDay}" else mDay}/${if(mMonth<9)"0${mMonth+1}" else mMonth+1}/$mYear"
                btnDataNascimento.error = null
            },
            mYear,
            mMonth,
            mDay
        )

        datePickerDialog.show()
    }

    private fun bindView() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}