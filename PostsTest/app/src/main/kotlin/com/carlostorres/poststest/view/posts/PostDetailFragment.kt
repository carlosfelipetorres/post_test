package com.carlostorres.poststest.view.posts


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlostorres.poststest.R
import com.carlostorres.poststest.databinding.FragmentPostDetailBinding
import com.carlostorres.poststest.injection.ViewModelFactory
import com.carlostorres.poststest.model.Post
import com.carlostorres.poststest.view.MainActivity
import com.carlostorres.poststest.view.posts.viewmodels.PostViewModel


class PostDetailFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var viewModel: PostViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail, container, false)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))[PostViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))[PostViewModel::class.java]
        binding.commentsList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        val post = arguments?.getSerializable("post") as Post
        viewModel.bind(post)
        viewModel.loadUserInfo(post.userId)
        viewModel.loadComments(post.id)
        viewModel.setPostRead(post)

        (activity as MainActivity).let {
            it.setSupportActionBar(binding.toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            it.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow)
            it.supportActionBar?.setDisplayShowHomeEnabled(true)
            it.supportActionBar?.title = ""
        }

        binding.favorite.setOnClickListener {
            viewModel.setPostFavorite(post, false)
        }
        binding.noFavorite.setOnClickListener {
            viewModel.setPostFavorite(post, true)
        }
        super.onViewCreated(view, savedInstanceState)
    }

}
