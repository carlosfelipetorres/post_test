package com.carlostorres.poststest.view.posts.viewmodels

import androidx.lifecycle.MutableLiveData
import com.carlostorres.poststest.R
import com.carlostorres.poststest.base.BaseViewModel
import com.carlostorres.poststest.model.Comment
import com.carlostorres.poststest.model.Post
import com.carlostorres.poststest.model.database.PostDao
import com.carlostorres.poststest.model.User
import com.carlostorres.poststest.remote.PostApi
import com.carlostorres.poststest.view.posts.adapter.CommentsListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostViewModel(private val postDao: PostDao? = null) : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()
    private val postFavorite = MutableLiveData<Boolean>()
    private val postRead = MutableLiveData<Boolean>()
    private var user = MutableLiveData<User>()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val commentsListAdapter: CommentsListAdapter = CommentsListAdapter()

    private lateinit var subscription: Disposable

    fun bind(post: Post) {
        postTitle.value = post.title
        postBody.value = post.body
        postFavorite.value = post.favorite
        postRead.value = post.read
    }

    fun getPostTitle(): MutableLiveData<String> {
        return postTitle
    }

    fun getPostBody(): MutableLiveData<String> {
        return postBody
    }
    fun getPostFavorite(): MutableLiveData<Boolean> {
        return postFavorite
    }

    fun getPostRead(): MutableLiveData<Boolean> {
        return postRead
    }

    fun getUser(): MutableLiveData<User> {
        return user
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun loadUserInfo(id: Int) {
        subscription = postApi.getUserById(id).map { it }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onRetrieveUserSuccess(result) },
                        { onRetrieveUserError() }
                )
    }

    fun loadComments(id: Int) {
        subscription = postApi.getCommentsByPost(id).map { it }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onRetrieveCommentsSuccess(result) },
                        { onRetrieveCommentsError() }
                )
    }

    private fun onRetrieveUserSuccess(user: User) {
        this.user.value = user
    }

    private fun onRetrieveUserError() {
        errorMessage.value = R.string.post_error
    }

    private fun onRetrieveCommentsSuccess(comments: List<Comment>) {
        commentsListAdapter.updateCommentsList(comments)
    }

    private fun onRetrieveCommentsError() {
        errorMessage.value = R.string.post_error
    }

    fun setPostFavorite(post: Post, favorite: Boolean) {
        post.favorite = favorite
        postDao?.update(post)
        postFavorite.value = favorite
    }

    fun setPostRead(post: Post) {
        post.read = true
        postDao?.update(post)
    }
}