package com.assessment.news.activities

import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.assessment.news.R
import com.assessment.news.activities.adapters.MainPageAdapter
import com.assessment.news.databinding.ActivityMainBinding
import com.assessment.news.utils.ConnectivityReceiver
import com.assessment.news.viewModel.MainContentViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.util.Locale.filter

class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener
   {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainContentViewModel
    private lateinit var movieAdapter : MainPageAdapter
    var languages = arrayOf("Java", "PHP", "Kotlin", "Javascript", "Python", "Swift")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        prepareRecyclerView()
        viewModel = ViewModelProvider(this)[MainContentViewModel::class.java]
        viewModel.getPopularMovies()
        viewModel.observeMovieLiveData().observe(this, Observer { movieList ->
            movieAdapter.setNewsList(movieList)
        })

        viewModel.observeFilterList().observe(this, Observer { filterList ->
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, filterList
            )
            binding.ivFilter.adapter = adapter
        })




        binding.ivFilter.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(this@MainActivity,
                            "" + languages[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }

        }
    }
        private fun prepareRecyclerView() {
            movieAdapter = MainPageAdapter()
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(applicationContext, 1)
                adapter = movieAdapter
            }
        }

        override fun onNetworkConnectionChanged(isConnected: Boolean) {
            showNetworkMessage(isConnected)
        }

        private fun showNetworkMessage(isConnected: Boolean) {
            if (!isConnected) {

                binding.toolbar.setBackgroundColor(resources.getColor(R.color.no_internet))
                binding.txtNews.text = "No Internet Connection Available"
            } else {
                binding.toolbar.setBackgroundColor(resources.getColor(R.color.toolbarColor))
                binding.txtNews.text = "News"
            }
        }

        override fun onResume() {
            super.onResume()
            ConnectivityReceiver.connectivityReceiverListener = this
        }



}