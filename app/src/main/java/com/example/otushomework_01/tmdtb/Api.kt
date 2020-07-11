package com.example.otushomework_01.tmdtb

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "d4e8a757921b52440a0a9304d1b0f509",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}