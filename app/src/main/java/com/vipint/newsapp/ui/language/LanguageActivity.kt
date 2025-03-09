package com.vipint.newsapp.ui.language

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
import com.vipint.newsapp.data.model.Language
import com.vipint.newsapp.databinding.ActivityLanguageBinding
import com.vipint.newsapp.di.component.DaggerActivityComponent
import com.vipint.newsapp.di.modules.ActivityModule
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.news.NewsActivity
import com.vipint.newsapp.utils.AppConstants.NEWS_BY_LANGUAGE
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageBinding

    @Inject
    lateinit var languageViewmodel: LanguageViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        injectDependencies()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        viewModelObserver()
    }

    private fun initViews() {
        binding.appBar.apply {
            this.tvAppBarTitle.text =
                ContextCompat.getString(this@LanguageActivity, R.string.select_language)
            this.ivSearch.visibility = View.INVISIBLE
            this.ivAction.setImageDrawable(
                ContextCompat.getDrawable(
                    this@LanguageActivity,
                    R.drawable.ic_back
                )
            )
            this.ivAction.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun viewModelObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                languageViewmodel.languages.collectLatest {
                    when (it) {
                        is UIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@LanguageActivity, it.message, Toast.LENGTH_SHORT)
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
                            initLanguageRV(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun initLanguageRV(data: List<Language>) {
        val countriesAdapter = LanguageAdapter(data)
        countriesAdapter.onItemClick = { _, selectedLanguage ->
            if (selectedLanguage.size == 2) {
                val selectedLanguageString = getStringPairFromList(selectedLanguage, transform = { it.id }).joinToString(",")
                binding.btnProceed.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        startActivity(
                            NewsActivity.getStartIntent(
                                context = this@LanguageActivity,
                                language = selectedLanguageString,
                                newsType = NEWS_BY_LANGUAGE
                            )
                        )
                    }
                }

            } else {
                binding.btnProceed.visibility = View.GONE
            }
        }

        binding.rvNewsSources.apply {
            adapter = countriesAdapter
            hasFixedSize()
        }

    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    private inline fun <T, R> getStringPairFromList(
        languageList: List<T>,
        transform: (T) -> R
    ): List<R> {
        return languageList.map {
            transform(it)
        }
    }
}