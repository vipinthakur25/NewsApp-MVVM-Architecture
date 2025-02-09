package com.vipint.newsapp.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vipint.newsapp.data.model.Country
import com.vipint.newsapp.databinding.NewsTypeLayoutBinding

class CountriesAdapter(
    private val countriesList: List<Country>,
    private val onClick: (Country) -> Unit
) :
    RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountriesAdapter.CountriesViewHolder {
        return CountriesViewHolder(
            NewsTypeLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CountriesAdapter.CountriesViewHolder, position: Int) {
        val data = countriesList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = countriesList.size

    inner class CountriesViewHolder(private val binding: NewsTypeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            binding.root.setOnClickListener {
                onClick.invoke(country)
            }
            binding.tvTypeTitle.text = country.name
        }

    }

}