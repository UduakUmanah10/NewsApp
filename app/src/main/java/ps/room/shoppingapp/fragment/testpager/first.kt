package ps.room.shoppingapp.fragment.testpager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ps.room.shoppingapp.R
import ps.room.shoppingapp.activity.NewsActivity
import ps.room.shoppingapp.adapters.NewsAdapter
import ps.room.shoppingapp.databinding.FragmentFirstBinding
import ps.room.shoppingapp.mvvm.NewsViewModel
import ps.room.shoppingapp.util.NetworkResponse

class first : Fragment(R.layout.fragment_first) {
    private lateinit var binding: FragmentFirstBinding
    val TAG = "BreakingNewsFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("article", it)
            }
            findNavController().navigate(R.id.action_newsHomeFragment_to_viewArticle, bundle)
        }

        viewModel._breakingNews.observe(
            viewLifecycleOwner,
            Observer { response ->
                when (response) {
                    is NetworkResponse.successful -> {
                        hideprogressbar()
                        response.data?.let {
                            newsAdapter.differ.submitList(it.articles)
                        }
                    }
                    is NetworkResponse.Error -> {
                        hideprogressbar()
                        response.message?.let {
                            Log.e(TAG, "An error occured: $it")
                        }
                    }
                    is NetworkResponse.Loading -> {
                        showprogressbar()
                    }
                }
            }
        )
    }

    private fun hideprogressbar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showprogressbar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
