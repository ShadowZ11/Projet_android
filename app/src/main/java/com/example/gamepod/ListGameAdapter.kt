package com.example.gamepod

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.gamepod.gameDetails.GameDetailsActivity
import com.google.rpc.context.AttributeContext.Resource
import com.squareup.picasso.Picasso
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors

class ListGameAdapter(private val games: List<GamePreview>) : RecyclerView.Adapter<ListGameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.list_games_element, parent, false)
        return ViewHolder(layout)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, GameDetailsActivity::class.java)
            intent.putExtra("game", games[position])
            intent.putExtra("idGame", games[position].idGame)
            holder.itemView.context.startActivity(intent)
        }
        val game = games[position]
        holder.title.text = game.title
        holder.description.text = game.description
        if (game.price.toFloat() == 0f){
            holder.price.text =  "Gratuit"
        }else{
            holder.price.text =  "Prix: " + game.price + "€"
        }
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val url = URL(game.image)
                val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                holder.picture.post {
                    holder.picture.setImageBitmap(bitmap)
                    Picasso.get().load(url.toString()).resize(300, 200).into(holder.picture)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


    }

    override fun getItemCount() = games.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.picture_game)
        val title: TextView = itemView.findViewById(R.id.title_game)
        val description: TextView = itemView.findViewById(R.id.description_game)
        val price: TextView = itemView.findViewById(R.id.price_game)
    }
}