package ps.room.shoppingapp.newsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import ps.room.shoppingapp.R
import ps.room.shoppingapp.activity.NewsActivity
import ps.room.shoppingapp.databinding.FragmentViewArticleBinding
import ps.room.shoppingapp.mvvm.NewsViewModel

class ViewArticle : Fragment(R.layout.fragment_view_article) {
    lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentViewArticleBinding
    val args by navArgs<ViewArticleArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewArticleBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,  "Article Saved Sucessfully",Snackbar.LENGTH_SHORT).show()
        }
    }
}
