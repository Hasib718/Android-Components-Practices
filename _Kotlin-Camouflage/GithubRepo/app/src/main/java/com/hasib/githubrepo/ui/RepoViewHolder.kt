package com.hasib.githubrepo.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hasib.githubrepo.R
import com.hasib.githubrepo.databinding.ItemRepoViewBinding
import com.hasib.githubrepo.model.Repo

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class RepoViewHolder(binding: ItemRepoViewBinding) : RecyclerView.ViewHolder(binding.root) {
    private val name: TextView = binding.repoName
    private val description: TextView = binding.repoDescription
    private val stars: TextView = binding.repoStars
    private val language: TextView = binding.repoLanguage
    private val forks: TextView = binding.repoForks

    private var repo: Repo? = null

    init {
        binding.root.setOnClickListener {
            repo?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                binding.root.context.startActivity(intent)
            }
        }
    }

    fun bind(repo: Repo?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: Repo) {
        this.repo = repo
        name.text = repo.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.description != null) {
            description.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

        stars.text = repo.stars.toString()
        forks.text = repo.forks.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!repo.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            language.text = resources.getString(R.string.language, repo.language)
            languageVisibility = View.VISIBLE
        }
        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val binding = ItemRepoViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RepoViewHolder(binding)
        }
    }
}
