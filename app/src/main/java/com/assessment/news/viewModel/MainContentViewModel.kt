package com.assessment.news.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.assessment.news.apis.RetrofitHelper
import com.assessment.news.apis.RetrofitInterface
import com.assessment.news.data.db.NewsDao
import com.assessment.news.data.db.NewsDatabase
import com.assessment.news.data.model.MainContent
import com.assessment.news.utils.subscribeOnBackground
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainContentViewModel(app: Application) : AndroidViewModel(app) {
    private var noteDao: NewsDao
    private val database = NewsDatabase.getInstance(app)
    private var movieLiveData = MutableLiveData<List<MainContent>>()


    init {
        noteDao = database.newsDao()
    }

    fun getPopularMovies() {

        val quotesApi = RetrofitHelper.getInstance().create(RetrofitInterface::class.java)

        GlobalScope.launch {
            val result = quotesApi.getQuotes()
            for(i in result.body()?.indices!!){
                insert(result.body()!![i])
            }
            movieLiveData.postValue(result.body())

        }
    }

    fun observeMovieLiveData(): LiveData<List<MainContent>> {
        return movieLiveData
    }

    fun insert(note: MainContent) {
       subscribeOnBackground {
            noteDao.insertAll(note)
       }
    }
}