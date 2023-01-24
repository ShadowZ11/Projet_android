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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailsGame : AppCompatActivity() {

    var buttonChooseDescription = true

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_game)

        val recyclerViewReviews = RecyclerView(this)
        recyclerViewReviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewReviews.itemAnimator = DefaultItemAnimator()
        recyclerViewReviews.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL)
        )

        val viewReviews = findViewById<LinearLayout>(R.id.view_group)

        val descriptionButton = findViewById<TextView>(R.id.description_path)
        descriptionButton.setBackgroundResource(R.drawable.custom_decription_full)
        val reviewButton = findViewById<TextView>(R.id.reviews_path)

        reviewButton.setOnClickListener{
            if (!buttonChooseDescription){
                return@setOnClickListener
            }
            buttonChooseDescription = false
            reviewButton.setBackgroundResource(R.drawable.custom_opinion_full)
            descriptionButton.setBackgroundResource(R.drawable.custom_decription)
            loadReviews(viewReviews, recyclerViewReviews)
        }
        descriptionButton.setOnClickListener {
            if (buttonChooseDescription){
                return@setOnClickListener
            }
            buttonChooseDescription = true
            reviewButton.setBackgroundResource(R.drawable.custom_opinion)
            descriptionButton.setBackgroundResource(R.drawable.custom_decription_full)
            loadDescriptionGame(viewReviews)
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

    private fun loadReviews(view: LinearLayout, recyclerViewReviews: RecyclerView){
        view.removeAllViews()
        //val reviewsView: View = layoutInflater.inflate(R.layout.reviews_details_game, view, false)
        val reviews = listOf(
            ItemReviewsView("Game 1", 0.9f,"Description for Game 1"),
            ItemReviewsView("Game 2", 5f,"Description for Game 2"),
            ItemReviewsView("Game 3", 2f,"Description for Game 3"),
            ItemReviewsView("Game 1", 2.04f,"Description for Game 1"),
            ItemReviewsView("Game 2", 1.6f,"Description for Game 2"),
            ItemReviewsView("Game 3", 3f,"Description for Game 3")
        )
        val adapter = AdapterRecyclerViewReviewsDG(reviews)
        recyclerViewReviews.adapter = adapter
        view.addView(recyclerViewReviews)
        view.requestLayout()
    }

}