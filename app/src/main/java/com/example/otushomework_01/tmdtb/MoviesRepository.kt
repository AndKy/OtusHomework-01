package com.example.otushomework_01.tmdtb

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {

    interface GetMoviesCallback {
        fun onSuccess(movies: List<Movie>)
        fun onError()
    }

    private val api: Api

    init {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getPopularMovies(page: Int = 1, callback: GetMoviesCallback) {
        api.getPopularMovies(page = page).enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            callback.onSuccess(responseBody.movies)
                        } else {
                            callback.onError()
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    callback.onError()
                }
            })
    }
}
