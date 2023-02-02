package com.example.gamepod

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date

    //class json pour les avis
/*    data class AuthorReviews(
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
    )*/

/*    data class ReviewsSummary(
        val num_reviews: Int,
        val review_score: Int,
        val review_score_desc: String,
        val total_positive: Int,
        val total_negative: Int,
        val total_reviews: Int
    )*/

    data class Reviews(
        @SerializedName("steamGameId")
        val idGame: Int,
        @SerializedName("steamReviewId")
        val idReview: Int,
        val userResume: String,
        val description: String,
        val rating: Float
    )


    //class json pour les jeux les plus joués
    data class Ranks(
        val rank: Int,
        val appId: Int,
        val last_week_rank: Int,
        val peak_in_game: Int
    )

    data class ResponseRanking(
        val rollup_date: Int,
        val ranks: List<Ranks>
    )

    data class Ranking(
        val response: ResponseRanking
    )


    //class best sales
    data class GamesId(
        val appid: Int
    )

    data class Items(
        val name: String,
        val start_of_month: Int,
        val url_path: String,
        val items: List<GamesId>
    )
    data class PagesSales(
        val pages: List<Items>
    )

    data class ResponseBestSales(
        val response: PagesSales
    )

    //class all Games
    data class Games(
        val appid: Int,
        val name: String
    )
    data class Apps(
        val apps: List<Games>
    )

    data class ResponseAllGames(
        val applist: Apps
    )


    //Class pour la récupération du jeu
   /* data class Game(
        @SerializedName("data")
        val data: Map<String, JsonObject>
    )*/

    data class Game(
        @SerializedName("steamGameId")
        val id: Int,
        val name: String,
        val description: String,
        val editorName: String,
        val price: Int,
        val review: List<Reviews>
    )

    data class WishList(
        val userId: Int,
        val games: List<Int>
    )

    data class LikeList(
        val userId: Int,
        val games: List<Int>
    )

    //Get Game
    data class getGame(
        val id: Int
    )

interface API {

    @GET("/ISteamApps/GetAppList/v2/")
    fun getAllGames(): Call<ResponseAllGames>

    @GET("/ISteamChartsService/GetTopReleasesPages/v1/")
    fun getTopRealease(): Call<ResponseBestSales>

    @GET("/ISteamChartsService/GetMostPlayedGames/v1/?")
    fun getRanking(): Call<Ranking>

    @GET("/getGame")
    fun getGameByName(): Call<getGame>

    @GET("/app/games/steamGameId/{id}")
    fun getGames(@Query("id") id: String): Call<Game>

    @GET("/appreviews/{id}")
    fun getOpinionGame(@Path("id") id: Long, @Query("json") ok: String): Call<Reviews>

    @GET("/users/{id}")
    fun getUser(@Path("id") id: Int)

}

object Request{

    private val api = Retrofit.Builder()
        .baseUrl("gfsdgsd")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(API::class.java)

    suspend fun getAllGames(): ResponseAllGames{
        return api.getAllGames().await()
    }
    suspend fun getTopRealease(): ResponseBestSales {
        return api.getTopRealease().await()
    }
    suspend fun getRanking(): Ranking {
        return api.getRanking().await()
    }

    suspend fun getGame(): getGame{
        return api.getGameByName().await()
    }

    suspend fun getOpinionGame(id: Long, value: String = "1"): Reviews{
        return api.getOpinionGame(id, value).await()
    }

}

