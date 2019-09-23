package com.carlostorres.poststest.view.posts


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlostorres.poststest.R
import com.carlostorres.poststest.databinding.FragmentListBinding
import com.carlostorres.poststest.injection.ViewModelFactory
import com.carlostorres.poststest.view.posts.viewmodels.PostListViewModel
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: PostListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))[PostListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))[PostListViewModel::class.java]
        viewModel.page = arguments?.getInt(BUNDLE_KEY_PAGE, 0)
        binding.postList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        viewModel.errorMessage.observe(this, Observer {
            errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        viewModel.navigation.observe(this, Observer {
            it?.let {
                val args = Bundle()
                args.putSerializable("post", it)
                if (findNavController().currentDestination?.id == R.id.postsListFragment) {
                    findNavController().navigate(R.id.action_postsListFragment_to_postDetailFragment, args)
                }
            }
        })
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    companion object {
        private const val BUNDLE_KEY_PAGE = "page"

        fun newInstance(page: Int): ListFragment {
            val listFragment = ListFragment()
            val args = Bundle()
            args.putInt(BUNDLE_KEY_PAGE, page)
            listFragment.arguments = args
            return listFragment
        }
    }
}