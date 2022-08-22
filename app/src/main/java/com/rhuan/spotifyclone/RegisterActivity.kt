package com.rhuan.spotifyclone

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.CharacterPickerDialog
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import com.rhuan.spotifyclone.databinding.ActivityRegisterBinding
import java.time.Month
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        val textVoltar = binding.voltar
        val btnRegistrar = binding.registrar
        val btnDataNascimento = binding.dataNascimento
        textVoltar.setOnClickListener(View.OnClickListener {
            finish()
        })

        btnRegistrar.setOnClickListener(View.OnClickListener {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                startActivity(Intent(this, HomePage().javaClass))
                finish()
            }, 1000);
            btnRegistrar.setOnClickListener(null)
        })



        btnDataNascimento.setOnClickListener(View.OnClickListener {
            datePickerShow(btnDataNascimento)
        })

    }

    private fun datePickerShow(btnDataNascimento:AppCompatButton){
        var c: Calendar = Calendar.getInstance();
        var mYear = c.get(Calendar.YEAR);
        var mMonth = c.get(Calendar.MONTH);
        var mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = DatePickerDialog(
            this,R.style.DatePickerTheme,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                btnDataNascimento.text = "$mDay/${if(mMonth<10)"0${mMonth+1}" else mMonth+1}/$mYear"
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