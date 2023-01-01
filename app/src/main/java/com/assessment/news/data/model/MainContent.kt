package com.assessment.news.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "news_table")
data class MainContent (
    @PrimaryKey
    val id: Int,
    val title: String?,
    val description: String?,
    val source: String?,
    val sourceId: String?,
    val version: String?,
    val publishedAt: Long?,
    val readablePublishedAt: String?,
    val updatedAt: Long?,
    val readableUpdatedAt: String?,
    val type: String?,
    val active: Boolean?,
    val draft: Boolean?,
    val embedTypes: String?,
    @TypeConverters(ImagesConverters::class)
    val images: Images,
    val language: String?
        )


public class ImagesConverters {

    @TypeConverter
    fun fromImagesJson(stat: Images): String {
        return Gson().toJson(stat)
    }



    @TypeConverter
    fun toImagesList(jsonImages: String): Images {
        val notesType = object : TypeToken<Images>() {}.type
        return Gson().fromJson<Images>(jsonImages, notesType)
    }
}