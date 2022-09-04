package com.rhuan.spotifyclone

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rhuan.spotifyclone.databinding.ActivityPlayerBinding
import com.squareup.picasso.Picasso
import java.util.*

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPlayerBinding
    private var musicPlaying = false
    private var musicFirstPlay = true
    private var musicPlayer:MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()

        val musicName = binding.musicName
        val musicArtist = binding.musicArtist
        val musicImage = binding.musicImage
        val musicButton = binding.musicButton
        val musicMaxTime = binding.musicMaxTime
        val musicActualTime = binding.actualTime
        val nextButton = binding.nextButton
        val previousButton = binding.previousButton

        val musics = intent.getSerializableExtra("music_player_list") as Array<Music>
        var music = intent.getSerializableExtra("music_player_object") as Music

        val seekBar = binding.progressBar

        musics.forEach { if(it == music) println("IGUAAAAL") }

        if (music != null){
            musicName.text = music.name
            musicArtist.text = music.artist

            Picasso.get().load(music.image).into(musicImage)

            musicPlayer = MediaPlayer.create(this,Uri.parse(music.music))
            musicMaxTime.text = "${musicPlayer!!.duration / 1000 / 60}:${musicPlayer!!.duration / 1000 % 60}"
            var timer = Timer()

            var timerTask = object:TimerTask(){
                override fun run() {
                    if (musicPlayer != null) {
                        runOnUiThread {
                            musicActualTime.text = "${musicPlayer!!.currentPosition / 1000 / 60}:${
                                if(musicPlayer!!.currentPosition / 1000 % 60 == 0) "00" 
                                else if (musicPlayer!!.currentPosition / 1000 % 60 < 10)"0${musicPlayer!!.currentPosition / 1000 % 60}"
                                else musicPlayer!!.currentPosition / 1000 % 60
                            }"
                        }
                        val mCurrentPosition: Double = musicPlayer!!.currentPosition / musicPlayer!!.duration.toDouble()
                        seekBar.progress = (mCurrentPosition * 100).toInt()
                    }
                }
            }

            nextButton.setOnClickListener{
                for (i in musics.indices){
                    if(musics[i] == music){
                        if(i != musics.size-1 && musics[i+1] != null){
                            music = musics[i+1]
                            Picasso.get().load(music.image).into(musicImage)
                            musicName.text = music.name
                            musicArtist.text = music.artist
                            timer.cancel()
                            timer.purge()
                            musicPlayer!!.reset()
                            musicPlayer!!.stop()
                            musicPlayer!!.release()
                            musicPlayer = MediaPlayer.create(this,Uri.parse(music.music))
                            musicMaxTime.text = "${musicPlayer!!.duration / 1000 / 60}:${musicPlayer!!.duration / 1000 % 60}"
                            musicPlayer!!.start()
                            musicButton.setImageDrawable(getDrawable(R.drawable.ic_pause))
                            timer = Timer()

                            var task = object:TimerTask(){
                                override fun run() {
                                    if (musicPlayer != null) {
                                        runOnUiThread {
                                            musicActualTime.text = "${musicPlayer!!.currentPosition / 1000 / 60}:${
                                                if(musicPlayer!!.currentPosition / 1000 % 60 == 0) "00"
                                                else if (musicPlayer!!.currentPosition / 1000 % 60 < 10)"0${musicPlayer!!.currentPosition / 1000 % 60}"
                                                else musicPlayer!!.currentPosition / 1000 % 60
                                            }"
                                        }
                                        val mCurrentPosition: Double = musicPlayer!!.currentPosition / musicPlayer!!.duration.toDouble()
                                        seekBar.progress = (mCurrentPosition * 100).toInt()
                                    }
                                }
                            }

                            timer.scheduleAtFixedRate(task,0,1000)
                            break
                        }
                        else if (i == musics.size-1){

                            timer.cancel()
                            timer.purge()

                            musicPlayer!!.reset()
                            musicPlayer!!.stop()
                            musicPlayer!!.release()
                            musicPlayer = MediaPlayer.create(this,Uri.parse(music.music))
                            musicPlayer!!.start()
                            musicButton.setImageDrawable(getDrawable(R.drawable.ic_pause))
                            Toast.makeText(applicationContext,
                                "Última música alcançada",
                                Toast.LENGTH_SHORT).show()

                            timer = Timer()

                            var task = object:TimerTask(){
                                override fun run() {
                                    if (musicPlayer != null) {
                                        runOnUiThread {
                                            musicActualTime.text = "${musicPlayer!!.currentPosition / 1000 / 60}:${
                                                if(musicPlayer!!.currentPosition / 1000 % 60 == 0) "00"
                                                else if (musicPlayer!!.currentPosition / 1000 % 60 < 10)"0${musicPlayer!!.currentPosition / 1000 % 60}"
                                                else musicPlayer!!.currentPosition / 1000 % 60
                                            }"
                                        }
                                        val mCurrentPosition: Double = musicPlayer!!.currentPosition / musicPlayer!!.duration.toDouble()
                                        seekBar.progress = (mCurrentPosition * 100).toInt()
                                    }
                                }
                            }

                            timer.scheduleAtFixedRate(task,0,1000)

                            break
                        }
                    }
                }
            }

            previousButton.setOnClickListener{
                for (i in musics.indices){
                    if(musics[i] == music){
                        if(i != 0 && musics[i-1] != null){
                            if(musicPlayer?.currentPosition!! / 1000 >= 10){
                                musicPlayer!!.reset()
                                musicPlayer!!.stop()
                                musicPlayer!!.release()
                                musicPlayer = MediaPlayer.create(this,Uri.parse(music.music))
                                musicPlayer!!.start()
                                musicButton.setImageDrawable(getDrawable(R.drawable.ic_pause))
                                return@setOnClickListener
                            }
                            music = musics[i-1]
                            Picasso.get().load(music.image).into(musicImage)
                            musicName.text = music.name
                            musicArtist.text = music.artist
                            timer.cancel()
                            timer.purge()
                            musicPlayer!!.reset()
                            musicPlayer!!.stop()
                            musicPlayer!!.release()
                            musicPlayer = MediaPlayer.create(this,Uri.parse(music.music))
                            musicMaxTime.text = "${musicPlayer!!.duration / 1000 / 60}:${musicPlayer!!.duration / 1000 % 60}"
                            musicPlayer!!.start()
                            musicButton.setImageDrawable(getDrawable(R.drawable.ic_pause))
                            timer = Timer()

                            var task = object:TimerTask(){
                                override fun run() {
                                    if (musicPlayer != null) {
                                        runOnUiThread {
                                            musicActualTime.text = "${musicPlayer!!.currentPosition / 1000 / 60}:${
                                                if(musicPlayer!!.currentPosition / 1000 % 60 == 0) "00"
                                                else if (musicPlayer!!.currentPosition / 1000 % 60 < 10)"0${musicPlayer!!.currentPosition / 1000 % 60}"
                                                else musicPlayer!!.currentPosition / 1000 % 60
                                            }"
                                        }
                                        val mCurrentPosition: Double = musicPlayer!!.currentPosition / musicPlayer!!.duration.toDouble()
                                        seekBar.progress = (mCurrentPosition * 100).toInt()
                                    }
                                }
                            }

                            timer.scheduleAtFixedRate(task,0,1000)
                            break
                        }
                        else if (i == 0){
                            timer.cancel()
                            timer.purge()

                            musicPlayer!!.stop()
                            musicPlayer!!.release()
                            musicPlayer = MediaPlayer.create(this,Uri.parse(music.music))
                            musicPlayer!!.start()
                            musicButton.setImageDrawable(getDrawable(R.drawable.ic_pause))
                            Toast.makeText(applicationContext,
                                "Primeira música alcançada",
                                Toast.LENGTH_SHORT).show()

                            timer = Timer()

                            var task = object:TimerTask(){
                                override fun run() {
                                    if (musicPlayer != null) {
                                        runOnUiThread {
                                            musicActualTime.text = "${musicPlayer!!.currentPosition / 1000 / 60}:${
                                                if(musicPlayer!!.currentPosition / 1000 % 60 == 0) "00"
                                                else if (musicPlayer!!.currentPosition / 1000 % 60 < 10)"0${musicPlayer!!.currentPosition / 1000 % 60}"
                                                else musicPlayer!!.currentPosition / 1000 % 60
                                            }"
                                        }
                                        val mCurrentPosition: Double = musicPlayer!!.currentPosition / musicPlayer!!.duration.toDouble()
                                        seekBar.progress = (mCurrentPosition * 100).toInt()
                                    }
                                }
                            }

                            timer.scheduleAtFixedRate(task,0,1000)
                            break
                        }
                    }
                }
            }

            timer.scheduleAtFixedRate(timerTask,0,1000)

            seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if(p2) {
                        var percent: Double = p1 / 100.0
                        val time = musicPlayer!!.duration * percent
                        musicPlayer?.seekTo(time.toInt())
                        musicActualTime.text = "${musicPlayer!!.currentPosition / 1000 / 60}:${
                            if(musicPlayer!!.currentPosition / 1000 % 60 == 0) "00"
                            else if (musicPlayer!!.currentPosition / 1000 % 60 < 10)"0${musicPlayer!!.currentPosition / 1000 % 60}"
                            else musicPlayer!!.currentPosition / 1000 % 60
                        }"
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    timer.cancel()
                    timer.purge()
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    timer = Timer()

                    var task = object:TimerTask(){
                        override fun run() {
                            if (musicPlayer != null) {
                                runOnUiThread {
                                    musicActualTime.text = "${musicPlayer!!.currentPosition / 1000 / 60}:${
                                        if(musicPlayer!!.currentPosition / 1000 % 60 == 0) "00"
                                        else if (musicPlayer!!.currentPosition / 1000 % 60 < 10)"0${musicPlayer!!.currentPosition / 1000 % 60}"
                                        else musicPlayer!!.currentPosition / 1000 % 60
                                    }"
                                }
                                val mCurrentPosition: Double = musicPlayer!!.currentPosition / musicPlayer!!.duration.toDouble()
                                seekBar.progress = (mCurrentPosition * 100).toInt()
                            }
                        }
                    }

                    timer.scheduleAtFixedRate(task,0,1000)
                }
            })

            musicButton.setOnClickListener{
                if (musicPlayer!!.isPlaying){
                    musicButton.setImageDrawable(getDrawable(R.drawable.ic_play))
                    musicPlayer!!.pause()

                } else {
                    musicButton.setImageDrawable(getDrawable(R.drawable.ic_pause))
                    musicPlayer!!.start()
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer?.stop()
        musicPlayer = null
    }

    private fun bindView(){
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}