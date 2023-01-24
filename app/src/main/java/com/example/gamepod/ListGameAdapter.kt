package com.example.gamepod

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListGameAdapter(private val games: List<GamePreview>) : RecyclerView.Adapter<ListGameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.list_games_element, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, GameDetailActivity::class.java)
            intent.putExtra("game", games[position])
            holder.itemView.context.startActivity(intent)
        }
        val game = games[position]
        holder.title.text = game.title
        holder.description.text = game.description
        holder.price.text = "Price: " + game.price + "€"
    }

    override fun getItemCount() = games.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.picture_game)
        val title: TextView = itemView.findViewById(R.id.title_game)
        val description: TextView = itemView.findViewById(R.id.description_game)
        val price: TextView = itemView.findViewById(R.id.price_game)
    }
}