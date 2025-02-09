package com.vipint.newsapp.data.repository

import com.vipint.newsapp.data.model.Country
import com.vipint.newsapp.utils.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCountryRepository @Inject constructor() {
    fun getCountries(): Flow<List<Country>> {
        return flow {
            emit(AppConstants.COUNTRIES)
        }
    }
}