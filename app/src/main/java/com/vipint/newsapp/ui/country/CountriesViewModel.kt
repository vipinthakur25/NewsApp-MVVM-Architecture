package com.vipint.newsapp.ui.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.model.Country
import com.vipint.newsapp.data.repository.GetCountryRepository
import com.vipint.newsapp.di.DispatchersProvider
import com.vipint.newsapp.ui.base.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountriesViewModel @Inject constructor(
    private val countryRepository: GetCountryRepository,
    private val dispatchersProvider: DispatchersProvider
) :
    ViewModel() {

    private val _countries = MutableStateFlow<UIState<List<Country>>>(UIState.Idle)
    val countries: StateFlow<UIState<List<Country>>> = _countries

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            countryRepository.getCountries()
                .flowOn(dispatchersProvider.default)
                .catch {
                    _countries.value = UIState.Error(it.toString())
                }.collectLatest {
                    _countries.value = UIState.Success(it)
                }
        }
    }

}