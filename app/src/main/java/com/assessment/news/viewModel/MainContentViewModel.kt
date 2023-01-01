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

        /*insert(
            MainContent(
                45, "fdgd",
                "fdgd",
                "fdgd",
                "fdgd",
                "fdgd",
                456,
                "fdgd",
                4353,
                "fdgd",
                "fdgd", true,
                true,
                "fdgd",
                "fdgd",
                "fdgd"
            )
        )*/
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