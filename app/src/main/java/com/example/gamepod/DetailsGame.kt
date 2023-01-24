package com.example.gamepod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailsGame : AppCompatActivity() {

    private val recyclerViewReviews: RecyclerView = RecyclerView(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_game)

        recyclerViewReviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewReviews.itemAnimator = DefaultItemAnimator()

        val viewReviews = findViewById<LinearLayout>(R.id.view_group)

        val descriptionButton = findViewById<TextView>(R.id.description_path)
        descriptionButton.setBackgroundResource(R.drawable.custom_decription_full)
        val reviewButton = findViewById<TextView>(R.id.reviews_path)

        reviewButton.setOnClickListener{
            reviewButton.setBackgroundResource(R.drawable.custom_opinion_full)
            descriptionButton.setBackgroundResource(R.drawable.custom_decription)
        }
        descriptionButton.setOnClickListener {
            reviewButton.setBackgroundResource(R.drawable.custom_opinion)
            descriptionButton.setBackgroundResource(R.drawable.custom_decription_full)
        }

        val returnButton = findViewById<ImageView>(R.id.back_button)
        returnButton.setOnClickListener {
            finish()
        }

    }


    private fun loadDescriptionGame(view: LinearLayout){

        view.removeAllViews()
        val descriptionView: View = layoutInflater.inflate(R.layout.description_details_game, view, false)
        view.addView(descriptionView)
        view.requestLayout()

    }

    private fun loadReviews(view: LinearLayout){
        view.removeAllViews()
        val reviewsView: View = layoutInflater.inflate(R.layout.description_details_game, view, false)
        view.addView(reviewsView)
        view.requestLayout()
    }

}