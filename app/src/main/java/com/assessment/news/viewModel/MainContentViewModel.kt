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
    private var newsLiveData = MutableLiveData<List<MainContent>>()
    private var filterList = MutableLiveData<List<String>>()


    init {
        noteDao = database.newsDao()
    }

    fun getNews() {
        val quotesApi = RetrofitHelper.getInstance().create(RetrofitInterface::class.java)
        subscribeOnBackground {
            newsLiveData.postValue(noteDao.getAllNews())
        }


        GlobalScope.launch {
            val result = quotesApi.getQuotes()
            noteDao.delete()
            for (i in result.body()?.indices!!) {
                insert(result.body()!![i])
            }
            newsLiveData.postValue(result.body())
            filterList.postValue(getFilterData(result.body()!!))
        }
    }

    fun observeNewsLiveData(): LiveData<List<MainContent>> {
        return newsLiveData
    }

    fun observeFilterList(): LiveData<List<String>> {
        return filterList
    }

    fun insert(note: MainContent) {
        subscribeOnBackground {
            noteDao.insertAll(note)
        }
    }

    fun getFilterData(newsData: List<MainContent>): List<String> {
        val newList = arrayListOf<String>()
        var newList1 = arrayListOf<String>()
        newList.add("Select")
        for (i in newsData.indices) {
            newList.add(newsData.get(i).type!!)
        }
        newList1 = newList.distinct().toList() as ArrayList<String>
        return newList1
    }
}