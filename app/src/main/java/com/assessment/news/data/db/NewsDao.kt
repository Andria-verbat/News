package com.assessment.news.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.assessment.news.data.model.MainContent


@Dao
interface NewsDao {
    @Insert
    fun insertAll( data: List<MainContent>)

    /*@Query("SELECT * FROM news_table")
    fun getAllNews(): LiveData<List<MainContent>>

    @Query("SELECT * FROM news_table Where type=:type")
    fun getNewsWithType(type: String): LiveData<List<MainContent>>


    @Query("DELETE FROM news_table")
    fun delete()*/
}