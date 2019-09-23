package com.carlostorres.poststest.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.carlostorres.poststest.model.database.AppDatabase
import com.carlostorres.poststest.view.posts.viewmodels.PostListViewModel
import com.carlostorres.poststest.view.posts.viewmodels.PostViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{

    private val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "posts").allowMainThreadQueries().build()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostListViewModel(db.postDao()) as T
        }
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(db.postDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}