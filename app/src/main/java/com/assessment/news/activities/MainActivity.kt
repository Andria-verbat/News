package com.assessment.news.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.assessment.news.R
import com.assessment.news.activities.adapters.MainPageAdapter
import com.assessment.news.data.db.NewsDatabase
import com.assessment.news.databinding.ActivityMainBinding
import com.assessment.news.viewModel.MainContentViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainContentViewModel
    private lateinit var movieAdapter : MainPageAdapter
    private lateinit var newsDatabase: NewsDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // newsDatabase=NewsDatabase.getInstance(this)
        //Insert Case
       /* val thread = Thread {
            var bookEntity = BookEntity()
            bookEntity.bookId = 1
            bookEntity.bookName = "Kotlin for Android Developer"

            newsDatabase.newsDao().saveBooks(bookEntity)

            //fetch Records
            newsDatabase.newsDao().getAllBooks().forEach()
            {
                Log.i("Fetch Records", "Id:  : ${it.bookId}")
                Log.i("Fetch Records", "Name:  : ${it.bookName}")
            }
        }
        thread.start()*/

        prepareRecyclerView()
        viewModel = ViewModelProvider(this)[MainContentViewModel::class.java]
        viewModel.getPopularMovies()
        viewModel.observeMovieLiveData().observe(this, Observer { movieList ->
            movieAdapter.setNewsList(movieList)
        })

    }
    private fun prepareRecyclerView() {
        movieAdapter = MainPageAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = movieAdapter
        }
    }


}