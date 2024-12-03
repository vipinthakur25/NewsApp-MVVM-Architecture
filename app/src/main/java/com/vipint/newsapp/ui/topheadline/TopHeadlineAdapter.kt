package com.vipint.newsapp.ui.topheadline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.databinding.TopHeadlineItemLayoutBinding

class TopHeadlineAdapter(private val itemList: List<ArticlesItem>) :
    RecyclerView.Adapter<TopHeadlineAdapter.TopHeadlineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHeadlineViewHolder =
        TopHeadlineViewHolder(
            TopHeadlineItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: TopHeadlineViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class TopHeadlineViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem) {
            if (item.title != "[Removed]") {
                with(binding) {
                    tvTitle.text = item.title
                    tvSource.text = item.source?.name
                    tvPublishedAt.text = item.publishedAt
                    Glide.with(ivNewsThumbnail.context).load(item.urlToImage).into(ivNewsThumbnail)
                }
            }


        }
    }
}