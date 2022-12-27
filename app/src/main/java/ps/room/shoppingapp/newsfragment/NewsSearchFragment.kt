package ps.room.shoppingapp.newsfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ps.room.shoppingapp.R
import ps.room.shoppingapp.activity.NewsActivity
import ps.room.shoppingapp.adapters.NewsAdapter
import ps.room.shoppingapp.constants.constants.SEARCH_DELAY_TIME
import ps.room.shoppingapp.databinding.FragmentNewsSearchBinding
import ps.room.shoppingapp.mvvm.NewsViewModel
import ps.room.shoppingapp.util.NetworkResponse

class NewsSearchFragment : Fragment(
    R.layout.fragment_news_search

) {

    private lateinit var binding: FragmentNewsSearchBinding
    val TAG = "BreakingNewsFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as NewsActivity).viewModel
        var job: Job? = null

        binding.apply {
            searchNews.addTextChangedListener { inputText ->
                job?.cancel()
                job = MainScope().launch {
                    delay(SEARCH_DELAY_TIME)
                    inputText?.let {
                        if (inputText.toString().isNotEmpty()) {
                            viewModel.searchForNews(inputText.toString())
                        }
                    }
                }
            }
        }
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("article", it)
            }
            findNavController().navigate(R.id.action_newsSearchFragment_to_viewArticle, bundle)
        }

        viewModel._searchNews.observe(
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
        binding.newsSearch.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
