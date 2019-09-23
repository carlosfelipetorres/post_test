package com.carlostorres.poststest.view.posts.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.carlostorres.poststest.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.carlostorres.poststest.base.BaseViewModel
import com.carlostorres.poststest.model.Post
import com.carlostorres.poststest.model.database.PostDao
import com.carlostorres.poststest.remote.PostApi
import com.carlostorres.poststest.view.posts.adapter.PostListAdapter
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao) : BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi
    val postListAdapter: PostListAdapter = PostListAdapter({
        navigation.value = it
        navigation.value = null
    }, false)

    val favoriteListAdapter: PostListAdapter = PostListAdapter({
        navigation.value = it
        navigation.value = null
    }, true)

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }
    val navigation: MutableLiveData<Post> = MutableLiveData()
    var page: Int? = 0

    private lateinit var subscription: Disposable

    init {
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun loadPosts() {
        subscription = Observable.fromCallable { postDao.all }
                .concatMap { dbPostList ->
                    if (dbPostList.isEmpty())
                        postApi.getPosts().concatMap { apiPostList ->
                            postDao.insertAll(*apiPostList.toTypedArray())
                            Observable.just(apiPostList)
                        }
                    else
                        Observable.just(dbPostList)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe(
                        { result -> onRetrievePostListSuccess(result) },
                        { onRetrievePostListError(it.message.toString()) }
                )
    }

    fun deleteAllPosts() {
        postDao.deleteAll()
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: List<Post>) {
        when (page) {
            0 -> postListAdapter.updatePostList(postList)
            else -> favoriteListAdapter.updatePostList(postList)
        }
    }

    private fun onRetrievePostListError(message: String) {
        Log.e("ERROR", message)
        errorMessage.value = R.string.post_error
    }
}