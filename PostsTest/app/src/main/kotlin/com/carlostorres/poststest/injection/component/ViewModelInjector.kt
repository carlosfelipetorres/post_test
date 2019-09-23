package com.carlostorres.poststest.injection.component

import dagger.Component
import com.carlostorres.poststest.injection.module.NetworkModule
import com.carlostorres.poststest.view.posts.viewmodels.CommentViewModel
import com.carlostorres.poststest.view.posts.viewmodels.PostListViewModel
import com.carlostorres.poststest.view.posts.viewmodels.PostViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjector {

    fun inject(postListViewModel: PostListViewModel)
    fun inject(postViewModel: PostViewModel)
    fun inject(commentViewModel: CommentViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}