package com.rhuan.spotifyclone

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MusicAdapter(private val musics:List<Music>,private val resId:Int): RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {

        lateinit var view:View

        if (resId == 1){
            view = LayoutInflater.from(parent.context).inflate(R.layout.music_list_item,parent,false)
        } else{
            view = LayoutInflater.from(parent.context).inflate(R.layout.artist_music_list_item,parent,false)
        }

        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(musics[position])
    }

    override fun getItemCount() = musics.size

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(music: Music) {

            itemView.setOnClickListener{
                var intent =Intent(itemView.context,PlayerActivity::class.java)
                intent.putExtra("music_player_object",music)
                intent.putExtra("music_player_list",musics.toTypedArray())
                itemView.context.startActivity(intent)

            }

            Picasso.get().load(music.image).into(itemView.findViewById<ImageView>(R.id.music_image))
            if(resId == 1) itemView.findViewById<TextView>(R.id.music_name).text = music.name
            itemView.findViewById<TextView>(R.id.music_artist).text = music.artist
        }
    }
}