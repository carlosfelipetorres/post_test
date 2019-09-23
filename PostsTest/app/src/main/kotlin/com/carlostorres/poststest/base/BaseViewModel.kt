package com.carlostorres.poststest.base

import androidx.lifecycle.ViewModel
import com.carlostorres.poststest.injection.component.DaggerViewModelInjector
import com.carlostorres.poststest.injection.component.ViewModelInjector
import com.carlostorres.poststest.injection.module.NetworkModule
import com.carlostorres.poststest.view.posts.viewmodels.CommentViewModel
import com.carlostorres.poststest.view.posts.viewmodels.PostListViewModel
import com.carlostorres.poststest.view.posts.viewmodels.PostViewModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
            is PostViewModel -> injector.inject(this)
            is CommentViewModel -> injector.inject(this)
        }
    }
}