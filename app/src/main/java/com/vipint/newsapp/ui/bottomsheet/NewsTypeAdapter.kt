package com.vipint.newsapp.ui.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vipint.newsapp.databinding.NewsTypeLayoutBinding
import com.vipint.newsapp.utils.NewsType

class NewsTypeAdapter(val typeList: List<NewsType>, val onItemClick: (NewsType) -> Unit) :
    RecyclerView.Adapter<NewsTypeAdapter.NewsTypeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsTypeViewHolder {
        return NewsTypeViewHolder(
            NewsTypeLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = typeList.size

    override fun onBindViewHolder(holder: NewsTypeViewHolder, position: Int) {
        holder.bind(typeList[position])
    }

    inner class NewsTypeViewHolder(val binding: NewsTypeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsType: NewsType) {
            binding.tvTypeTitle.text = newsType.type
            binding.root.setOnClickListener {
                onItemClick.invoke(newsType)
            }
        }
    }

}