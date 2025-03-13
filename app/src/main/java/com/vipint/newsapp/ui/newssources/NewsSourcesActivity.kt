package com.vipint.newsapp.ui.newssources

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
import com.vipint.newsapp.data.model.SourcesItem
import com.vipint.newsapp.databinding.ActivityNewsSourcesBinding
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.news.NewsActivity
import com.vipint.newsapp.utils.AppConstants.NEWS_BY_SOURCES
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsSourcesActivity : AppCompatActivity() {
    private lateinit var newsSourcesViewModel: NewsSourcesViewModel
    private lateinit var binding: ActivityNewsSourcesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewsSourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModel()
        initView()
        setUpObserver()
    }

    private fun setupViewModel() {
        newsSourcesViewModel = ViewModelProvider(this)[NewsSourcesViewModel::class.java]
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSourcesViewModel.uiState.collectLatest {
                    when (it) {
                        is UIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@NewsSourcesActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        UIState.Idle -> {

                        }

                        UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
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

    private fun renderUi(data: List<SourcesItem>?) {
        data?.let {
            val newsSourcesAdapter = NewsSourcesAdapter(it)
            newsSourcesAdapter.onItemClick = { _, data ->
                startActivity(
                    NewsActivity.getStartIntent(
                        this,
                        newsSource = data.id,
                        newsType = NEWS_BY_SOURCES
                    )
                )

            }
            binding.rvNewsSources.apply {
                adapter = newsSourcesAdapter
                hasFixedSize()
            }
        }

    }

    private fun initView() {
        binding.appBar.apply {
            this.tvAppBarTitle.text =
                ContextCompat.getString(this@NewsSourcesActivity, R.string.news_sources)
            this.ivSearch.visibility = View.INVISIBLE
            this.ivAction.setImageDrawable(
                ContextCompat.getDrawable(
                    this@NewsSourcesActivity,
                    R.drawable.ic_back
                )
            )
            this.ivAction.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}