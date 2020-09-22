package com.example.planboard.ui.news.endpoint

import com.example.planboard.ui.news.data.NewsData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsEndpoint {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Observable<NewsData>
}