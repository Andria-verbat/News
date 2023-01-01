package com.assessment.news.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.assessment.news.data.model.ImagesConverters
import com.assessment.news.data.model.MainContent

@Database(entities = [(MainContent::class)], version = 1, exportSchema = false)
@TypeConverters(ImagesConverters::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao() : NewsDao
    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context) : NewsDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, NewsDatabase::class.java, "NEWS_DATABASE")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

                return INSTANCE!!

            }
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

            }
        }

    }
}