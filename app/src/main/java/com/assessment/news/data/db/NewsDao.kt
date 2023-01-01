package com.assessment.news.data.db

import androidx.room.Dao
import androidx.room.Insert
import com.assessment.news.data.model.MainContent


@Dao
interface NewsDao {

    @Insert
    fun insertAll( data: MainContent)

    /*@Query("SELECT * FROM news_table")
    fun getAllNews(): LiveData<List<MainContent>>

    @Query("SELECT * FROM news_table Where type=:type")
    fun getNewsWithType(type: String): LiveData<List<MainContent>>

    @Query("DELETE FROM news_table")
    fun delete()*/
}