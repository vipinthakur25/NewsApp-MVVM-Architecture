package com.vipint.newsapp.ui.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.model.Language
import com.vipint.newsapp.data.repository.GetLanguageRepository
import com.vipint.newsapp.ui.base.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageViewmodel @Inject constructor(private val getLanguageRepository: GetLanguageRepository) :
    ViewModel() {
    private val _languages = MutableStateFlow<UIState<List<Language>>>(UIState.Idle)
    val languages: StateFlow<UIState<List<Language>>> = _languages

    init {
        getLanguage()
    }


    private fun getLanguage() {
        viewModelScope.launch {
            getLanguageRepository.getLanguages()
                .flowOn(Dispatchers.Default)
                .catch {
                    _languages.value = UIState.Error(it.toString())
                }.collectLatest {
                    _languages.value = UIState.Success(it)
                }
        }
    }
}