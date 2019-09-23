package com.carlostorres.poststest.model.database

import androidx.room.*
import com.carlostorres.poststest.model.Post
import androidx.room.FtsOptions.Order




@Dao
interface PostDao {
    @get:Query("SELECT * FROM post")
    val all: List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg post: Post)

    @Update
    fun update(post: Post)

    @Query("DELETE FROM post")
    fun deleteAll()
}