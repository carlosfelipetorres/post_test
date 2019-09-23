package com.carlostorres.poststest

import com.carlostorres.poststest.model.database.PostDao
import com.carlostorres.poststest.remote.PostApi
import com.carlostorres.poststest.view.posts.viewmodels.PostListViewModel
import io.reactivex.Observable
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.junit.Before
import org.mockito.Mock


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Mock
    var apiEndPoint: PostApi? = null
    private var viewModel: PostListViewModel? = null

    @Mock
    var postDao: PostDao? = null


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = postDao?.let { PostListViewModel(it) }
    }

    @Test
    fun testNull() {
        `when`(apiEndPoint!!.getPosts()).thenReturn(null)
        assertNotNull(viewModel!!.loadPosts())
    }

    @Test
    fun testApiFetchDataSuccess() {
        `when`(apiEndPoint!!.getPosts()).thenReturn(Observable.just(listOf()))
        viewModel!!.loadPosts()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        apiEndPoint = null
        viewModel = null
    }
}
