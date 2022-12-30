package com.assessment.news.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.assessment.news.apis.RetrofitHelper
import com.assessment.news.apis.RetrofitInterface
import com.assessment.news.data.db.NewsDatabase
import com.assessment.news.data.model.MainContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketException

class MainContentViewModel : ViewModel() {
    private var movieLiveData = MutableLiveData<List<MainContent>>()
    fun getPopularMovies() {

        val quotesApi = RetrofitHelper.getInstance().create(RetrofitInterface::class.java)

        GlobalScope.launch {
            val result = quotesApi.getQuotes()
            movieLiveData.postValue(result.body())
        }
    }
    fun observeMovieLiveData() : LiveData<List<MainContent>> {
        return movieLiveData
    }
}