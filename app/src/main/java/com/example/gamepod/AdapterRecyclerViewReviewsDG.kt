package com.example.gamepod

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolderRecyclerReview(view: View): RecyclerView.ViewHolder(view){

    private val userName: TextView = view.findViewById(R.id.name_user)
    private val rating: RatingBar = view.findViewById(R.id.note_review)
    private val reviewDescription: TextView = view.findViewById(R.id.review_description)


    fun bindValue(userName: String, rating_value: Float, reviewDescription: String){
        this.userName.text = userName
        this.rating.rating = rating_value
        this.reviewDescription.text = reviewDescription
    }


}

class AdapterRecyclerViewReviewsDG(private val values: List<ItemReviewsView>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolderRecyclerReview(inflater.inflate(R.layout.reviews_details_game, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderRecyclerReview).bindValue(values[position].nameUser, values[position].rating_value, values[position].description_reviews)
    }

    override fun getItemCount(): Int {
        return values.size
    }

}
