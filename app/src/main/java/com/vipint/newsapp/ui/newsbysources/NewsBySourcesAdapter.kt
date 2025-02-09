package com.vipint.newsapp.ui.newsbysources

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.databinding.TopHeadlineItemLayoutBinding

class NewsBySourcesAdapter(private val itemList: List<ArticlesItem>) :
    RecyclerView.Adapter<NewsBySourcesAdapter.NewsBySourcesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsBySourcesViewHolder =
        NewsBySourcesViewHolder(
            TopHeadlineItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: NewsBySourcesViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class NewsBySourcesViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem) {
                with(binding) {
                    tvNewsTitle.text = item.title
                    tvNewsDesc.text = item.description
                    Glide.with(ivNewsThumbnail.context).load(item.urlToImage).into(ivNewsThumbnail)
                }
            binding.root.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(item.url))
            }
        }
    }
}