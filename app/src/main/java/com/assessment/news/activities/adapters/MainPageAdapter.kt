package com.assessment.news.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assessment.news.databinding.MainRecyclerviewElementBinding
import com.assessment.news.data.model.MainContent
import com.bumptech.glide.Glide


class MainPageAdapter : RecyclerView.Adapter<MainPageAdapter.ViewHolder>() {
    private var mainList = ArrayList<MainContent>()
    fun setNewsList(newsList : List<MainContent>){
        this.mainList = newsList as ArrayList<MainContent>
        notifyDataSetChanged()
    }
    class ViewHolder(val binding : MainRecyclerviewElementBinding) : RecyclerView.ViewHolder(binding.root)  {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MainRecyclerviewElementBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mainList[position].images.square_140)
            .into(holder.binding.newsImage)
        holder.binding.newsHeadline.text = mainList[position].readablePublishedAt?.substring(0,11)+": "+mainList[position].title
    }
    override fun getItemCount(): Int {
        return mainList.size
    }}