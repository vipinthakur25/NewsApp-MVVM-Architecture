package com.vipint.newsapp.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vipint.newsapp.NewsApplication
import com.vipint.newsapp.R
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.databinding.ActivitySearchBinding
import com.vipint.newsapp.di.component.DaggerActivityComponent
import com.vipint.newsapp.di.modules.ActivityModule
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.utils.getTextChangedStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var searchNewsViewModel: SearchNewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        injectDependencies()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        setUpObserver()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun setUpObserver() {
        lifecycleScope.launch {
            binding.searchLayout.etSearch.getTextChangedStateFlow()
                .debounce(300)
                .filter {
                    if (it.isEmpty()) {
                        binding.searchLayout.etSearch.setText("")
                        return@filter false
                    } else {
                        return@filter true
                    }
                }.distinctUntilChanged()
                .flatMapLatest { query ->
                    searchNewsViewModel.getSearchNews(query)
                }
                .flowOn(Dispatchers.Default)
                .collectLatest {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        when (it) {
                            is UIState.Error -> {
                                binding.progressBar.visibility = View.GONE
                                binding.rvNewsItem.visibility = View.GONE
                                Toast.makeText(this@SearchActivity, it.message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            UIState.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.rvNewsItem.visibility = View.GONE
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
            val searchNewsAdapter = SearchNewsAdapter(it)
            binding.rvNewsItem.apply {
                adapter = searchNewsAdapter
                hasFixedSize()
            }
        }
    }


    private fun initView() {
        binding.appBar.tvAppBarTitle.text = getString(R.string.search)
        binding.appBar.ivSearch.visibility = View.GONE
        binding.searchLayout.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


    }

    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().injectSearchActivity(this)
    }
}