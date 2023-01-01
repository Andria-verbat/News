package com.assessment.news.data.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.assessment.news.data.model.MainContent


@Dao
interface NewsDao {

    @Insert
    fun insertAll( data: MainContent)

    @Query("SELECT * FROM news_table")
    fun getAllNews(): List<MainContent>

    @Query("DELETE FROM news_table")
    fun delete()

}