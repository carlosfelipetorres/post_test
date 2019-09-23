package com.carlostorres.poststest.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlostorres.poststest.model.Post

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}