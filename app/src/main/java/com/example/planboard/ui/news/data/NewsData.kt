package com.example.planboard.ui.news.data

data class NewsData(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)