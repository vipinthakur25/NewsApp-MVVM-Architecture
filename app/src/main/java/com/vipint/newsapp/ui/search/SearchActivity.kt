package com.vipint.newsapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vipint.newsapp.R
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.databinding.ActivitySearchBinding
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.utils.getTextChangedStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchNewsViewModel: SearchNewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
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
        searchNewsViewModel = ViewModelProvider(this)[SearchNewsViewModel::class.java]
    }

    private fun setUpObserver() {
        val data = binding.searchLayout.etSearch.getTextChangedStateFlow()
        binding.searchLayout.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchNewsViewModel.getSearchKey(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchNewsViewModel.searchDataStateFlow.collectLatest {
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

}