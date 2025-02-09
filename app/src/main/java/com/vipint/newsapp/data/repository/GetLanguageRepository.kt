package com.vipint.newsapp.data.repository

import com.vipint.newsapp.data.model.Language
import com.vipint.newsapp.utils.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLanguageRepository @Inject constructor() {
    fun getLanguages(): Flow<List<Language>> {
        return flow {
            emit(AppConstants.LANGUAGES)
        }
    }
}