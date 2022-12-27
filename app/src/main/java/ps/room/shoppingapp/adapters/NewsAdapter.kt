package ps.room.shoppingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ps.room.shoppingapp.databinding.ArticlePreviewBinding
import ps.room.shoppingapp.dataclass.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    private lateinit var Binding: ArticlePreviewBinding

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differcallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differcallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        Binding = ArticlePreviewBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(Binding.root)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articlePosition = differ.currentList[position]

        Glide.with(holder.itemView)
            .load(articlePosition.urlToImage)
            .into(Binding.ivArticleImage)

        Binding.tvSource.text = articlePosition.source.name
        Binding.tvTitle.text = articlePosition.title
        Binding.tvDescription.text = articlePosition.description
        Binding.tvPublishedAt.text = articlePosition.publishedAt

        holder.itemView.apply {
            Binding.tvSource.text = articlePosition.source.name
            Binding.tvTitle.text = articlePosition.title
            Binding.tvDescription.text = articlePosition.description
            Binding.tvPublishedAt.text = articlePosition.publishedAt
            setOnClickListener {
                onItemClickListener?.let { it(articlePosition) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}
