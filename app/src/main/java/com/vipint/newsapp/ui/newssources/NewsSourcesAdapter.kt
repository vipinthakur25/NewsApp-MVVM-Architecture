package com.vipint.newsapp.ui.newssources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vipint.newsapp.data.model.SourcesItem
import com.vipint.newsapp.databinding.NewsTypeLayoutBinding

class NewsSourcesAdapter(
    private val newsSources: List<SourcesItem>,
    val onItemClick: (SourcesItem) -> Unit
) :
    RecyclerView.Adapter<NewsSourcesAdapter.NewsSourcesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NewsSourcesAdapter.NewsSourcesViewHolder {
        return NewsSourcesViewHolder(
            NewsTypeLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsSourcesAdapter.NewsSourcesViewHolder, position: Int) {
        holder.bind(newsSources[position])
    }

    override fun getItemCount(): Int = newsSources.size

    inner class NewsSourcesViewHolder(private val newsTypeLayoutBinding: NewsTypeLayoutBinding) :
        RecyclerView.ViewHolder(newsTypeLayoutBinding.root) {
        fun bind(newsSource: SourcesItem) {
            newsTypeLayoutBinding.tvTypeTitle.apply {
                this.text = newsSource.name
            }
            newsTypeLayoutBinding.root.setOnClickListener {
                onItemClick.invoke(newsSource)
            }
        }
    }

}