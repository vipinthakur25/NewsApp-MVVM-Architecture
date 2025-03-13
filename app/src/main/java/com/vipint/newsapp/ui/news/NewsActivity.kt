package com.vipint.newsapp.ui.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vipint.newsapp.R
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.databinding.ActivityNewsBinding
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.topheadline.TopHeadlineAdapter
import com.vipint.newsapp.utils.AppConstants.NEWS_BY_COUNTRY
import com.vipint.newsapp.utils.AppConstants.NEWS_BY_LANGUAGE
import com.vipint.newsapp.utils.AppConstants.NEWS_BY_SOURCES
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpObserver()
        initView()

    }

    private fun initView() {
        binding.appBar.apply {
            this.tvAppBarTitle.text =
                ContextCompat.getString(this@NewsActivity, R.string.news)
            this.ivSearch.visibility = View.INVISIBLE
            this.ivAction.setImageDrawable(
                ContextCompat.getDrawable(
                    this@NewsActivity, R.drawable.ic_back
                )
            )
            this.ivAction.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        getIntentData()

    }

    private fun getIntentData() {
        intent?.extras?.apply {
            val extraNewsType = this.getString(EXTRA_NEWS_TYPE)
            extraNewsType?.let { newsType ->
                when (newsType) {
                    NEWS_BY_SOURCES -> {
                        val extraSource = this.getString(EXTRA_NEWS_SOURCE)
                        extraSource?.let { source ->
                            newsViewModel.getNewsBySources(source)
                        }

                    }

                    NEWS_BY_COUNTRY -> {
                        val extraCountry = this.getString(EXTRA_COUNTRY_ID)
                        extraCountry?.let { country ->
                            newsViewModel.getNewsByCountry(country)
                        }
                    }


                    NEWS_BY_LANGUAGE -> {
                        val extraLanguage = this.getString(EXTRA_LANGUAGE)
                        extraLanguage?.let { language ->
                            newsViewModel.getNewsByLanguage(language)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val EXTRA_COUNTRY_ID = "EXTRA_COUNTRY_ID"
        private const val EXTRA_LANGUAGE = "EXTRA_LANGUAGE"
        private const val EXTRA_NEWS_SOURCE = "EXTRA_NEWS_SOURCE"
        private const val EXTRA_NEWS_TYPE = "EXTRA_NEWS_TYPE"
        fun getStartIntent(
            context: Context,
            newsSource: String? = "",
            countryId: String? = "",
            language: String? = "",
            newsType: String,
        ): Intent {
            return Intent(context, NewsActivity::class.java).apply {
                putExtra(EXTRA_NEWS_SOURCE, newsSource).putExtra(EXTRA_COUNTRY_ID, countryId)
                    .putExtra(EXTRA_LANGUAGE, language).putExtra(EXTRA_NEWS_TYPE, newsType)
            }
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsViewModel.uiState.collectLatest {
                    when (it) {
                        is UIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@NewsActivity, it.message, Toast.LENGTH_SHORT
                            ).show()
                        }

                        UIState.Idle -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        UIState.Loading -> {

                        }

                        is UIState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rvNewsSources.visibility = View.VISIBLE
                            renderUi(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun renderUi(articlesItems: List<ArticlesItem>?) {
        articlesItems?.let {
            val headlineAdapter = TopHeadlineAdapter(it)
            binding.rvNewsSources.apply {
                adapter = headlineAdapter
                hasFixedSize()
            }
            headlineAdapter.onItemClick = { _, data ->
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(this, Uri.parse(data.url))
            }
        }
    }

}