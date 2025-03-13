package com.vipint.newsapp.ui.country

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vipint.newsapp.R
import com.vipint.newsapp.data.model.Country
import com.vipint.newsapp.databinding.ActivityCountriesBinding
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.news.NewsActivity
import com.vipint.newsapp.utils.AppConstants.NEWS_BY_COUNTRY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesActivity : AppCompatActivity() {

    private lateinit var countriesViewModel: CountriesViewModel
    lateinit var binding: ActivityCountriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCountriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModel()
        initView()
        initObservers()
    }

    private fun setupViewModel() {
        countriesViewModel = ViewModelProvider(this)[CountriesViewModel::class.java]
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countriesViewModel.countries.collectLatest {
                    when (it) {
                        is UIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@CountriesActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        UIState.Idle -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is UIState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rvNewsSources.visibility = View.VISIBLE
                            initCountriesRV(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun initCountriesRV(data: List<Country>) {
        val countriesAdapter = CountriesAdapter(data)
        countriesAdapter.onItemClick = { _, data ->
            startActivity(
                NewsActivity.getStartIntent(
                    this,
                    newsType = NEWS_BY_COUNTRY,
                    countryId = data.id
                )
            )

        }
        binding.rvNewsSources.apply {
            adapter = countriesAdapter
            hasFixedSize()
        }
    }

    private fun initView() {
        binding.appBar.apply {
            this.tvAppBarTitle.text =
                ContextCompat.getString(this@CountriesActivity, R.string.select_country)
            this.ivSearch.visibility = View.INVISIBLE
            this.ivAction.setImageDrawable(
                ContextCompat.getDrawable(
                    this@CountriesActivity,
                    R.drawable.ic_back
                )
            )
            this.ivAction.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

}