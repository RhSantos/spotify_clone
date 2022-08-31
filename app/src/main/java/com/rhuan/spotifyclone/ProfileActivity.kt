package com.rhuan.spotifyclone

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rhuan.spotifyclone.databinding.ActivityProfileBinding
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var name:EditText
    private lateinit var username:EditText
    private lateinit var email:EditText
    private lateinit var birthDate:AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        mAuth = FirebaseAuth.getInstance()

        name = binding.name
        username = binding.username
        email = binding.email
        birthDate = binding.dataNascimento

        val sair = binding.sair
        val voltar = binding.voltar

        voltar.setOnClickListener(View.OnClickListener { finish() })
        sair.setOnClickListener(View.OnClickListener {
            if(mAuth.currentUser != null){
                mAuth.signOut()
                finish()
                startActivity(Intent(this,LoginOptionsActivity().javaClass))
            }
        })

        if(mAuth.currentUser != null){
            getUser()
        }
    }

    private fun getUser() {
        val userInfo = FirebaseDatabase.getInstance().getReference("Users")
            .child(mAuth.currentUser!!.uid)

        userInfo.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val userProfile = snapshot.getValue(User::class.java)

                name.setText(userProfile!!.name)
                username.setText(userProfile!!.username)
                email.setText(userProfile!!.email)
                birthDate.text = userProfile!!.birthDate

                name.visibility = View.VISIBLE
                username.visibility = View.VISIBLE
                email.visibility = View.VISIBLE
                birthDate.visibility = View.VISIBLE
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun bindView(){
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}