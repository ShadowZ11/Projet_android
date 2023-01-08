package com.example.gamepod

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    data class Opinion(
        val result: String
    )

    data class Ranking(
        val something: Int,
        val other: String

    )

    data class Game(
        val id: Int,
        val name: String
    )

    @GET("/ranking")
    fun getRanking() : Call<List<Ranking>>

    @GET("/getGame")
    fun getGames(@Query("appids") id: String): Call<Game>

    @GET("/opinion")
    fun getOpinionGame(@Query("json") id: String): Call<Opinion>


}