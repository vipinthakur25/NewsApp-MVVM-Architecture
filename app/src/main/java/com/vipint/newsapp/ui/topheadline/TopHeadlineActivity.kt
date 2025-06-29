package com.vipint.newsapp.ui.topheadline

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
import com.vipint.newsapp.NewsApplication
import com.vipint.newsapp.R
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.databinding.ActivityTopHeadlinesBinding
import com.vipint.newsapp.di.component.DaggerActivityComponent
import com.vipint.newsapp.di.modules.ActivityModule
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.bottomsheet.FilterNewsBottomSheetFragment
import com.vipint.newsapp.ui.bottomsheet.NewsTypeViewModel
import com.vipint.newsapp.ui.country.CountriesActivity
import com.vipint.newsapp.ui.language.LanguageActivity
import com.vipint.newsapp.ui.newssources.NewsSourcesActivity
import com.vipint.newsapp.ui.search.SearchActivity
import com.vipint.newsapp.utils.AppConstants.COUNTRY
import com.vipint.newsapp.utils.NewsType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {
    @Inject
    lateinit var newsListViewModel: TopHeadlinesViewModel

    @Inject
    lateinit var newsTypeViewModel: NewsTypeViewModel
    private lateinit var binding: ActivityTopHeadlinesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTopHeadlinesBinding.inflate(layoutInflater)
        injectDependencies()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUi()
        setUpObserver()

    }

    private fun initUi() {
        newsListViewModel.fetchNews(COUNTRY)
        binding.apply {
            this.appBar.tvAppBarTitle.text = getString(R.string.top_headline)
            this.appBar.ivSearch.visibility = View.VISIBLE
            this.appBar.ivAction.setImageDrawable(
                ContextCompat.getDrawable(
                    this@TopHeadlineActivity,
                    R.drawable.ic_news
                )
            )
            this.appBar.ivSearch.setOnClickListener {
                startActivity(Intent(this@TopHeadlineActivity, SearchActivity::class.java))
            }
            this.appBar.ivAction.setOnClickListener {
                navigateToDialog()
            }
        }

    }

    private fun navigateToDialog() {
        val typeLit = newsTypeViewModel.fetchNewsType()
        val bottomSheetDialogFragment = FilterNewsBottomSheetFragment.newInstance(typeLit)

        bottomSheetDialogFragment.onNewsSourceClick = {
            when (it) {
                NewsType.TOP_HEADLINE -> {

                }

                NewsType.NEWS_SOURCES -> {
                    startActivity(Intent(this, NewsSourcesActivity::class.java))
                }

                NewsType.COUNTRIES -> {
                    startActivity(Intent(this, CountriesActivity::class.java))

                }

                NewsType.LANGUAGE -> {
                    startActivity(Intent(this, LanguageActivity::class.java))

                }
            }
        }
        bottomSheetDialogFragment.show(supportFragmentManager, "FilterNewsSheetDialog")
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiState.collectLatest {
                    when (it) {
                        is UIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is UIState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rvNewsItem.visibility = View.VISIBLE
                            renderUi(it.data)
                        }

                        UIState.Idle -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }

    }

    private fun renderUi(articlesItems: List<ArticlesItem>?) {
        articlesItems?.let {
            val headlineAdapter = TopHeadlineAdapter(it)
            headlineAdapter.onItemClick = { _, data ->
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(this, Uri.parse(data.url))

            }
            binding.rvNewsItem.apply {
                adapter = headlineAdapter
                hasFixedSize()
            }
        }


    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}