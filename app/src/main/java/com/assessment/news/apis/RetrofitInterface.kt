package com.assessment.news.apis

import com.assessment.news.data.model.MainContent
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("items?lineupSlug=news")
    suspend fun getQuotes() : Response<List<MainContent>>
}