package com.carlostorres.poststest.remote

import com.carlostorres.poststest.model.Comment
import io.reactivex.Observable
import com.carlostorres.poststest.model.Post
import com.carlostorres.poststest.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: Int): Single<User>

    @GET("/comments")
    fun getCommentsByPost(@Query("postId") id: Int): Single<List<Comment>>
}