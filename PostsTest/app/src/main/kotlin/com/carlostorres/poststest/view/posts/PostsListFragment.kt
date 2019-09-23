package com.carlostorres.poststest.view.posts


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.carlostorres.poststest.R
import com.carlostorres.poststest.databinding.FragmentPostsListBinding
import com.carlostorres.poststest.injection.ViewModelFactory
import com.carlostorres.poststest.view.MainActivity
import com.carlostorres.poststest.view.posts.adapter.PostPagerAdapter
import com.carlostorres.poststest.view.posts.viewmodels.PostListViewModel
import com.google.android.material.tabs.TabLayout


class PostsListFragment : Fragment() {

    private lateinit var binding: FragmentPostsListBinding
    private lateinit var viewModel: PostListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts_list, container, false)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))[PostListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))[PostListViewModel::class.java]
        (activity as MainActivity).setSupportActionBar(binding.toolbar)

        binding.refresh.setOnClickListener {
            setViewPager()
        }
        binding.deleteAll.setOnClickListener {
            viewModel.deleteAllPosts()
            setViewPager()
        }
    }

    override fun onResume() {
        setViewPager()
        setViewPager()
        super.onResume()
    }

    private fun setViewPager() {
        val pager = binding.viewPager as ViewPager
        val adapter = PostPagerAdapter(childFragmentManager)
        pager.adapter = adapter
        (binding.tab as TabLayout).setupWithViewPager(pager, true)
    }
}
