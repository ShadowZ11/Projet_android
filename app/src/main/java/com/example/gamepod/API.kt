package com.example.gamepod

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


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

    data class IdGames(
        val appid: Int
    )
    data class InfoUserReview(
        @SerializedName("steamUserId")
        val idUser: String,
        @SerializedName("steamUserName")
        val nameUser: String
    )

    data class Reviews(
        @SerializedName("steamGameId")
        val idGame: Int,
        @SerializedName("steamReviewId")
        val idReview: String,
        val steamUserResume: InfoUserReview,
        val description: String,
        val votedUp: Boolean
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
        val pages: List<Page>
    )

    data class Page(
        val name: String,
        @SerializedName("item_ids")
        val items: List<IdGames>
    )

    data class ResponseBestSales(
        val response: PagesSales,
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
        val price: Float,
        val reviews: List<Reviews>,
        @SerializedName("logoURL")
        val logo: String,
        @SerializedName("iconURL")
        val icon: String
    )

    data class WishList(
        val userId: String,
        @SerializedName("gamesResumeId")
        val games: List<IdGames>
    )

    data class LikeList(
        val userId: String,
        @SerializedName("gamesResumeId")
        val games: List<IdGames>
    )

    data class WishListFragment(
        val userId: String,
        @SerializedName("gamesResumeId")
        val games: IdGames
    )

    data class LikeListFragment(
        val userId: String,
        @SerializedName("gamesResumeId")
        val games: IdGames
    )

    data class ResponseAPISuccess(
        val message: String
    )

    //Get Game
    data class getGame(
        val id: Int
    )

    data class getUser(
        val id: Int
    )


interface API {

    @GET("/ISteamApps/GetAppList/v2/")
    fun getAllGames(): Deferred<ResponseAllGames>

    @GET("/ISteamChartsService/GetTopReleasesPages/v1/")
    fun getTopRelease(): Deferred<ResponseBestSales>

    @GET("/ISteamChartsService/GetMostPlayedGames/v1/?")
    fun getRanking(): Deferred<Ranking>

    @GET("/app/gamesFull/gameName/{name}")
    fun getGameByName(@Path("name") name: String): Deferred<List<Game>>

    @GET("/app/gamesFull/steamGameId/{id}")
    fun getGameById(@Path("id") id: Int): Deferred<Game>

    @GET("/appreviews/{id}")
    fun getOpinionGame(@Path("id") id: Long, @Query("json") ok: String): Deferred<Reviews>

    @GET("/app/users/userId/{id}")
    fun getUser(@Path("id") id: Int): Deferred<getUser>

    @GET("/app/wishlists/userId/{userId}")
    fun getMyWishList(@Path("userId") id: String): Deferred<WishList>

    @GET("/app/likelists/userId/{userId}")
    fun getMyLikeList(@Path("userId") id: String): Deferred<LikeList>

    @POST("/app/wishlists")
    fun addToWishlist(@Body data: WishListFragment): Deferred<ResponseAPISuccess>

    @POST("/app/likelists")
    fun addToLikeList(@Body data: LikeListFragment): Deferred<ResponseAPISuccess>

    @PUT("/app/wishlists/userId/{userId}/add/gameResumeId/{gameId}")
    fun updateWishlist(@Path("userId") data: String, @Path("gameId") id: String): Deferred<ResponseAPISuccess>

    @PUT("/app/likelists/userId/{userId}/add/gameResumeId/{gameId}")
    fun updateLikeList(@Path("userId") data: String, @Path("gameId") id: String): Deferred<ResponseAPISuccess>

    @DELETE("/app/wishlists/userId/{userId}/delete/gameResumeId/{gameId}")
    fun deleteFromWishList(@Path("userId") data: String, @Path("gameId") id: String): Deferred<ResponseAPISuccess>

    @DELETE("/app/likelists/userId/{userId}/delete/gameResumeId/{gameId}")
    fun deleteFromLikeList(@Path("userId") data: String, @Path("gameId") id: String): Deferred<ResponseAPISuccess>


}

object Request{

    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.MINUTES)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl("https://us-central1-androidsteam-b9b14.cloudfunctions.net")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(API::class.java)

    private val apiSteam = Retrofit.Builder()
        .baseUrl("https://api.steampowered.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(API::class.java)

    private val apiSteamSales = Retrofit.Builder()
        .baseUrl("https://steamcommunity.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(API::class.java)


    suspend fun getAllGames(): ResponseAllGames{
        return apiSteam.getAllGames().await()
    }
    suspend fun getTopRelease(): ResponseBestSales {
        return apiSteam.getTopRelease().await()
    }
    suspend fun getRanking(): Ranking {
        return apiSteam.getRanking().await()
    }

    suspend fun getGameById(id: Int): Game{
        return api.getGameById(id).await()
    }

    suspend fun getGameByName(name: String): List<Game>{
        return api.getGameByName(name).await()
    }

    suspend fun getOpinionGame(id: Long, value: String = "1"): Reviews{
        return api.getOpinionGame(id, value).await()
    }

    suspend fun addToWishList(data: WishListFragment): ResponseAPISuccess{
        return api.addToWishlist(data).await()
    }
    suspend fun addToLikeList(data: LikeListFragment): ResponseAPISuccess{
        return api.addToLikeList(data).await()
    }
    suspend fun getLikeList(id: String): LikeList{
        return api.getMyLikeList(id).await()
    }

    suspend fun getMyWishList(id: String): WishList{
        return api.getMyWishList(id).await()
    }

    suspend fun updateWishList(data: String, id: String): ResponseAPISuccess{
        return api.updateWishlist(data, id).await()
    }
    suspend fun updateLikeList(data: String, id: String): ResponseAPISuccess{
        return api.updateLikeList(data, id).await()
    }

    suspend fun deleteLikeList(userId: String, id: String): ResponseAPISuccess{
        return api.deleteFromLikeList(userId, id).await()
    }
    suspend fun deleteWishList(userId: String, id: String): ResponseAPISuccess{
        return api.deleteFromWishList(userId, id).await()
    }

}

