package com.vipint.newsapp.ui.country

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vipint.newsapp.NewsApplication
import com.vipint.newsapp.R
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.model.Country
import com.vipint.newsapp.databinding.ActivityNewsByCountryBinding
import com.vipint.newsapp.di.component.DaggerActivityComponent
import com.vipint.newsapp.di.modules.ActivityModule
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.topheadline.TopHeadlineAdapter
import com.vipint.newsapp.ui.topheadline.TopHeadlinesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsByCountryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsByCountryBinding

    @Inject
    lateinit var newsListViewModel: TopHeadlinesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        injectDependencies()
        binding = ActivityNewsByCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUi()
        setUpObserver()

    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiState.collectLatest {
                    when (it) {
                        is UIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@NewsByCountryActivity,
                                it.message,
                                Toast.LENGTH_SHORT
                            )
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
            binding.rvNewsItem.apply {
                adapter = headlineAdapter
                hasFixedSize()
            }
        }


    }

    @SuppressLint("NewApi")
    private fun initUi() {
        val country = intent?.extras?.getParcelable("country", Country::class.java)
        country?.let {
            newsListViewModel.fetchNews(it.id)
        }
        binding.appBar.apply {
            this.tvAppBarTitle.text = country?.name
            this.ivSearch.visibility = View.INVISIBLE
            this.ivAction.setImageDrawable(
                ContextCompat.getDrawable(
                    this@NewsByCountryActivity,
                    R.drawable.ic_back
                )
            )
            this.ivAction.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}