package com.rhuan.spotifyclone

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.drawToBitmap
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rhuan.spotifyclone.databinding.ActivitySongRegisterBinding
import kotlinx.coroutines.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class SongRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongRegisterBinding
    lateinit var img: ImageView
    lateinit var musicName: EditText
    lateinit var artistName: EditText
    lateinit var musicBtn: AppCompatButton
    private var uri: Uri? = null
    private var musicUri: Uri? = null
    lateinit var imgLink: String
    lateinit var musicLink: String
    val db = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        val voltar = binding.voltar

        img = binding.image
        artistName = binding.nomeArtista
        musicName = binding.nomeMusica

        musicBtn = binding.arquivoMusica
        var filePath: String? = null
        val registrar = binding.registrarMusica

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                uri = it.data?.data!!
                decodeFile()
            }
        }

        val launcherMusic =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    musicUri = it.data?.data!!
                    musicBtn.text = getFileName(musicUri!!)!!
                    musicBtn.error = null
                }
            }

        voltar.setOnClickListener(View.OnClickListener { finish() })

        img.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launcher.launch(Intent.createChooser(intent, "Select Picture"))
        })

        musicBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "audio/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launcherMusic.launch(Intent.createChooser(intent, "Select Music"))
        })

        registrar.setOnClickListener(View.OnClickListener { registerSong() })
    }

    private fun registerSong() {
        val strMusicName = musicName.text
        val strArtistName = artistName.text

        /*var list = FirebaseDatabase.getInstance().getReference("Musics")
            .get().result.value*/

        val regex = Regex("[^a-zA-ZçÇáÁéÉúÚíÍóÓàÀèÈìÌòÒùÙãÃõÕâÂêÊîÎôÔûÛ]")
        when (true) {

            uri == null -> {
                Toast
                    .makeText(
                        this,
                        "Você deve selecionar uma imagem para a música",
                        Toast.LENGTH_LONG
                    )
                    .show()
                return
            }

            musicUri == null -> {
                musicBtn.error = ""
                Toast
                    .makeText(this, "Você deve selecionar uma música", Toast.LENGTH_LONG)
                    .show()
                return
            }

            strMusicName.isEmpty() -> {
                musicName.error = "Nome da música não pode ser vazio"
                return
            }

            strMusicName.matches(regex) -> {
                musicName.error = "Nome da música não pode conter caracteres especiais"
                return
            }

            strArtistName.isEmpty() -> {
                artistName.error = "Nome do artista não pode ser vazio"
                return
            }

            strArtistName.matches(regex) -> {
                artistName.error = "Nome do artista não pode conter caracteres especiais"
                return
            }

            else -> Unit
        }

        val dbReg = "${strMusicName.toString().replace(' ', '_').lowercase()}-${
            strArtistName.toString().replace(' ', '_').lowercase()
        }"

        uploadImgAndMusicToStorage(strMusicName.toString(),strArtistName.toString(),dbReg)
    }

    fun uploadMusicToDb(strMusicName:String,strArtistName:String,dbReg: String){

        val music = Music(imgLink, strMusicName, strArtistName, musicLink)

        FirebaseDatabase.getInstance().getReference("Musics")
            .child("$dbReg")
            .setValue(music).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast
                        .makeText(
                            this,
                            "Música Registrada com Sucesso",
                            Toast.LENGTH_LONG
                        )
                        .show()
                    musicName.setText("")
                    artistName.setText("")
                    img.setImageURI(null)
                    musicBtn.text = "Arquivo de música"
                }
            }
    }

    fun uploadImgAndMusicToStorage(strMusicName: String,strArtistName: String,dbReg: String) {

        val bitmap: Bitmap = img.drawToBitmap()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val bitmapData = bos.toByteArray()
        val bs = ByteArrayInputStream(bitmapData)

        val links = mutableListOf<String>()

        val uploadImg = db.reference.child("images/$dbReg").putStream(bs)
            .addOnSuccessListener {
                uri = null
                it.storage.downloadUrl.addOnCompleteListener { task ->
                    imgLink = task.result.toString()
                    val uploadMusic = db.reference.child("audio/$dbReg").putFile(musicUri!!)
                        .addOnSuccessListener {
                            musicUri = null

                            it.storage.downloadUrl.addOnCompleteListener { task ->
                                musicLink = task.result.toString()
                                uploadMusicToDb(strMusicName,strArtistName,dbReg)
                            }
                        }
                }
            }

    }

    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    private fun decodeFile() {
        img.setImageURI(uri)
    }

    private fun bindView() {
        binding = ActivitySongRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}