package com.example.gamepod

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class GameDetailActivity : AppCompatActivity() {

    private lateinit var viewGroup: LinearLayout
    private lateinit var descriptionPath: TextView
    private lateinit var reviewsPath: TextView

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_game)
        val game = intent.getParcelableExtra("game", GamePreview::class.java)
        val titleView = findViewById<TextView>(R.id.game_details_title)
        viewGroup = findViewById(R.id.view_group)
        descriptionPath = findViewById(R.id.description_path)
        reviewsPath = findViewById(R.id.reviews_path)

        if (game != null) {
            titleView.text = game.title

            descriptionPath.setOnClickListener {
                viewGroup.removeAllViews()
                val descriptionView = layoutInflater.inflate(R.layout.description_view, viewGroup, false)
                viewGroup.addView(descriptionView)
            }

            reviewsPath.setOnClickListener {
                viewGroup.removeAllViews()
                val reviewsView = layoutInflater.inflate(R.layout.reviews_view, viewGroup, false)
                viewGroup.addView(reviewsView)
            }
        }
        showDescription(viewGroup);
    }
    fun showDescription(view: View) {
        viewGroup.removeAllViews()
        val descriptionView = layoutInflater.inflate(R.layout.description_view, viewGroup, false)
        viewGroup.addView(descriptionView)
    }

    fun showReviews(view: View) {
        viewGroup.removeAllViews()
        val reviewsView = layoutInflater.inflate(R.layout.reviews_view, viewGroup, false)
        viewGroup.addView(reviewsView)
    }
}
