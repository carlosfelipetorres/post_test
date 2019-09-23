package com.carlostorres.poststest.view.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.carlostorres.poststest.R
import com.carlostorres.poststest.databinding.ItemPostBinding
import com.carlostorres.poststest.model.Post
import com.carlostorres.poststest.view.posts.viewmodels.PostViewModel

class PostListAdapter(private val onPostClick: (post: Post) -> Unit,
                      private val filterFavorite: Boolean):
        RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private lateinit var postList:List<Post>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return if(::postList.isInitialized) postList.size else 0
    }

    fun updatePostList(postList:List<Post>){
        this.postList = if (filterFavorite) postList.filter { it.favorite == true } else postList
        notifyDataSetChanged()
    }

    fun removeAt(adapterPosition: Int) {

    }

    inner class ViewHolder(private val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = PostViewModel()

        fun bind(post: Post){
            viewModel.bind(post)
            binding.root.setOnClickListener { onPostClick(post) }
            binding.vm = viewModel
        }
    }
}