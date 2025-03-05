package com.vipint.newsapp.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vipint.newsapp.data.model.Language
import com.vipint.newsapp.databinding.NewsTypeLayoutBinding
import com.vipint.newsapp.utils.ItemClickListener

class LanguageAdapter(
    private val languageList: List<Language>,

    ) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {
    lateinit var onItemClick: ItemClickListener<Language>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LanguageAdapter.LanguageViewHolder {
        return LanguageViewHolder(
            NewsTypeLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LanguageAdapter.LanguageViewHolder, position: Int) {
        val data = languageList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = languageList.size

    inner class LanguageViewHolder(private val binding: NewsTypeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(language: Language) {
            binding.root.setOnClickListener {
                onItemClick.invoke(adapterPosition, language)
            }
            binding.tvTypeTitle.text = language.name
        }

    }

}