package com.carlostorres.poststest.view.posts.viewmodels

import androidx.lifecycle.MutableLiveData
import com.carlostorres.poststest.base.BaseViewModel
import com.carlostorres.poststest.model.Comment
import com.carlostorres.poststest.remote.PostApi
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CommentViewModel : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi
    private val commentName = MutableLiveData<String>()
    private val commentEmail = MutableLiveData<String>()
    private val commentBody = MutableLiveData<String>()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable

    fun bind(comment: Comment) {
        commentName.value = comment.name
        commentEmail.value = comment.email
        commentBody.value = comment.body
    }

    fun getCommentName(): MutableLiveData<String> {
        return commentName
    }

    fun getCommentEmail(): MutableLiveData<String> {
        return commentEmail
    }

    fun getCommentBody(): MutableLiveData<String> {
        return commentBody
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}