package com.vipint.newsapp.ui.newsbysources

import android.content.Context
import android.content.Intent
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
import com.vipint.newsapp.databinding.ActivityNewsBySourcesBinding
import com.vipint.newsapp.di.component.DaggerActivityComponent
import com.vipint.newsapp.di.modules.ActivityModule
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.topheadline.TopHeadlineAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsBySourcesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBySourcesBinding

    @Inject
    lateinit var newsBySourcesViewModel: NewsBySourcesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        injectDependencies()
        binding = ActivityNewsBySourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        setUpObserver()
    }

    private fun initView() {
        val newsBySourceId = intent.getStringExtra(NEWS_SOURCE_ID)
        newsBySourceId?.let {
            newsBySourcesViewModel.getNewsBySources(it)
        }
        binding.appBar.apply {
            this.tvAppBarTitle.text =
                ContextCompat.getString(this@NewsBySourcesActivity, R.string.news_sources)
            this.ivSearch.visibility = View.INVISIBLE
            this.ivAction.setImageDrawable(
                ContextCompat.getDrawable(
                    this@NewsBySourcesActivity,
                    R.drawable.ic_back
                )
            )
            this.ivAction.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    companion object {
        private const val NEWS_SOURCE_ID = "news_source_id"

        fun start(context: Context, newsSourceId: String? = null) {
            val intent = Intent(context, NewsBySourcesActivity::class.java).apply {
                putExtra(NEWS_SOURCE_ID, newsSourceId)
            }
            context.startActivity(intent)
        }
    }

    private fun setUpObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsBySourcesViewModel.uiState.collectLatest {
                    when (it) {
                        is UIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@NewsBySourcesActivity, it.message, Toast.LENGTH_SHORT
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
        }

    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}