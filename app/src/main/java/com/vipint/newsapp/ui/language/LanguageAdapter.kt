package com.vipint.newsapp.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vipint.newsapp.R
import com.vipint.newsapp.data.model.Language
import com.vipint.newsapp.databinding.NewsTypeLayoutBinding
import com.vipint.newsapp.utils.ItemClickListener

class LanguageAdapter(
    private val languageList: List<Language>
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {
    var selectedLanguage: ArrayList<Language> = ArrayList()
    lateinit var onItemClick: ItemClickListener<ArrayList<Language>>
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): LanguageAdapter.LanguageViewHolder {
        return LanguageViewHolder(
            NewsTypeLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
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
            with(binding) {
                tvTypeTitle.text = language.name
                setBgAndTextColorOfText(
                    textView = tvTypeTitle,
                    unSelectedBgColor = R.color.white,
                    selectedBgColor = R.color.blue,
                    selectedTextColor = R.color.white,
                    unSelectedTextColor = R.color.black,
                    isSelected = language.isSelected
                )

                // Update selectedLanguage list based on initial state
                if (language.isSelected && !selectedLanguage.contains(language)) {
                    selectedLanguage.add(language)
                }

                root.setOnClickListener {
                    updateLanguageSelection(language, tvTypeTitle)
                    onItemClick.invoke(adapterPosition, selectedLanguage)
                    println("onClick Invoke")
                }

            }
        }

    }

    private fun updateLanguageSelection(language: Language, textView: AppCompatTextView) {
        if (language.isSelected) {
            selectedLanguage.remove(language)
            language.isSelected = false
        } else if (selectedLanguage.size < 2) {
            selectedLanguage.add(language)
            language.isSelected = true
        } else {
            return
        }

        setBgAndTextColorOfText(
            textView = textView,
            unSelectedBgColor = R.color.white,
            selectedBgColor = R.color.blue,
            selectedTextColor = R.color.white,
            unSelectedTextColor = R.color.black,
            isSelected = language.isSelected
        )
    }

    private fun setBgAndTextColorOfText(
        textView: AppCompatTextView,
        selectedBgColor: Int,
        unSelectedBgColor: Int,
        selectedTextColor: Int,
        unSelectedTextColor: Int,
        isSelected: Boolean
    ) {
        with(textView) {
            setBackgroundColor(
                if (isSelected) ContextCompat.getColor(
                    this.context,
                    selectedBgColor
                ) else ContextCompat.getColor(this.context, unSelectedBgColor)
            )
            setTextColor(
                if (isSelected) ContextCompat.getColor(
                    this.context,
                    selectedTextColor
                ) else ContextCompat.getColor(this.context, unSelectedTextColor)
            )

        }
    }
}