package com.example.gamepod

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

    data class AuthorReviews(
        val steamid: Int,
        val num_games_owned: Int,
        val num_reviews: Int,
        val playtime_forever: Int,
        val playtime_last_two_weeks: Int,
        val playtime_at_review: Int,
        val last_played: Int
    )
    data class ReviewsSent(
        val recommendationid : Int,
        val author: AuthorReviews,
        val language: String,
        val review: String,
        val timestamp_created: Date,
        val timestamp_updated: Date,
        val voted_up: Boolean,
        val votes_up: Int,
        val votes_funny: Int,
        val weighted_vote_score: Int,
        val comment_count: Int,
        val steam_purchase: Boolean,
        val received_for_free: Boolean,
        val written_during_early_access: Boolean,
        val developer_response: String,
        val timestamp_dev_responded: Date
    )

    data class ReviewsSummary(
        val num_reviews: Int,
        val review_score: Int,
        val review_score_desc: String,
        val total_positive: Int,
        val total_negative: Int,
        val total_reviews: Int
    )

    data class Reviews(
        val success: Int,
        val query_summary : ReviewsSummary,
        val cursor: Int,
        val reviews: List<ReviewsSent>,
        )

    data class Ranking(
        val success: Int,
        val other: String
    )

    data class Game(
        val id: Int,
        val name: String
    )

interface API {



    @GET("/ranking")
    fun getRanking() : Call<List<Ranking>>

    @GET("/getGame")
    fun getGames(@Query("appids") id: String): Call<Game>

    @GET("/opinion")
    fun getOpinionGame(@Query("json") id: String): Call<Reviews>


}