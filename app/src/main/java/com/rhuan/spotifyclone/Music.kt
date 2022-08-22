package com.rhuan.spotifyclone

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

data class Music(
    val image: Drawable?,
    val name: String,
    val artist: String
)

class MusicBuilder {

    var image: Drawable? = null
    var name: String = ""
    var artist: String = ""

    fun build(): Music = Music(image,name,artist)
}

fun music(block: MusicBuilder.() -> Unit):Music = MusicBuilder().apply(block).build()

fun fakeMusics(context: Context) = mutableListOf<Music>(
    Music(ContextCompat.getDrawable(context,R.drawable.anitta),"Girl from Rio","Anitta"),
    Music(ContextCompat.getDrawable(context,R.drawable.dua_lipa),"Levitating","Dua Lipa")
)