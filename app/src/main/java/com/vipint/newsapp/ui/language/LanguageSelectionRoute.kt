package com.vipint.newsapp.ui.language

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vipint.newsapp.data.model.Language
import com.vipint.newsapp.ui.base.ShowError
import com.vipint.newsapp.ui.base.ShowLoading
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.utils.getStringPairFromList

@Composable
fun LanguageSelectionRoute(
    languageViewmodel: LanguageViewmodel = hiltViewModel(), onLanguageItemClick: (String) -> Unit
) {
    val languageUiState by languageViewmodel.languages.collectAsStateWithLifecycle()
    LanguageListContent(languageUiState, onLanguageItemClick)

}

@Composable
fun LanguageListContent(uiState: UIState<List<Language>>, onLanguageItemClick: (String) -> Unit) {
    when (uiState) {
        is UIState.Error -> {
            ShowError(uiState.message)
        }

        UIState.Idle -> {

        }

        UIState.Loading -> {
            ShowLoading()
        }

        is UIState.Success -> {
            LanguageList(uiState.data, onLanguageItemClick)
        }
    }
}

@Composable
fun LanguageList(languageList: List<Language>, onSeeNewsClick: (String) -> Unit) {
    val selectedItems = remember {
        mutableStateListOf<Language>()
    }

    LazyColumn {
        items(languageList) { language ->
            val isSelected = selectedItems.contains(language)

            val languageCopy = language.copy(isSelected = isSelected)
            LanguageItem(languageCopy, onLanguageSelected = {
                if (isSelected) {
                    selectedItems.remove(language)
                } else {
                    if (selectedItems.size < 2) {
                        selectedItems.add(language)
                    }

                }
            })

        }
        item {
            Button(onClick = {
                onSeeNewsClick.invoke(
                    getStringPairFromList(selectedItems, transform = { it.id }).joinToString(",")
                )
            }, enabled = selectedItems.size >= 2, modifier = Modifier.padding(10.dp)) {
                Text(
                    "See news",
                    style = TextStyle(
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)

                )
            }
        }

    }
}

@Composable
fun LanguageItem(language: Language, onLanguageSelected: (Language) -> Unit) {

    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 12.dp, top = 5.dp, end = 12.dp, bottom = 5.dp)
        .clickable {
            onLanguageSelected.invoke(language)
        }
        .background(
            color = if (language.isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(size = 5.dp)
        )
        .padding(10.dp), text = language.name, style = TextStyle(
        fontSize = 16.sp, color = Color.White, textAlign = TextAlign.Center
    ))


}