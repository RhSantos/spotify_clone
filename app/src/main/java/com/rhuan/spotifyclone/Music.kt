package com.rhuan.spotifyclone

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import java.io.Serializable

data class Music(
    val image: String,
    val name: String,
    val artist: String,
    val music: String):Serializable {
    constructor():this("","","","")
}

class MusicBuilder:Serializable {

    var image: String = ""
    var name: String = ""
    var artist: String = ""
    var music: String = ""

    fun build(): Music = Music(image,name,artist,music)
}

fun music(block: MusicBuilder.() -> Unit):Music = MusicBuilder().apply(block).build()

fun fakeMusics(context: Context) = mutableListOf<Music>(
    Music("gs://spotify-clone-c1726.appspot.com/images/12312-41324321","Girl from Rio","Anitta",""),
    Music("https://m.media-amazon.com/images/I/71AyfVKzxLL._AC_SL1427_.jpg","Levitating","Dua Lipa","")
)