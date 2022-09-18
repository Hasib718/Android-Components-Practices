package com.hasib.basic_paging

import androidx.recyclerview.widget.RecyclerView
import com.hasib.basic_paging.data.Article
import com.hasib.basic_paging.data.createdText
import com.hasib.basic_paging.databinding.ArticleViewholderBinding

/**
 * View Holder for a [Article] RecyclerView list item.
 */
class ArticleViewHolder(
    private val binding: ArticleViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        binding.apply {
            binding.title.text = article.title
            binding.description.text = article.description
            binding.created.text = article.createdText
        }
    }
}
