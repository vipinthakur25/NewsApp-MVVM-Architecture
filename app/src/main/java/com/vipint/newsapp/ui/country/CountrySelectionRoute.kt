package com.vipint.newsapp.ui.country

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vipint.newsapp.data.model.Country
import com.vipint.newsapp.ui.base.ShowError
import com.vipint.newsapp.ui.base.ShowLoading
import com.vipint.newsapp.ui.base.UIState

@Composable
fun CountrySelectionRoute(
    countriesViewModel: CountriesViewModel = hiltViewModel(),
    onCountryItemClick: (String) -> Unit,
) {
    val countryUiState by countriesViewModel.countries.collectAsStateWithLifecycle()
    CountryListScreen(countryUiState, onCountryItemClick)
}

@Composable
fun CountryListScreen(
    countryUiState: UIState<List<Country>>,
    onCountryItemClick: (String) -> Unit,
) {
    when (countryUiState) {
        is UIState.Error -> {
            ShowError(countryUiState.message)
        }

        UIState.Idle -> {

        }

        UIState.Loading -> {
            ShowLoading()
        }

        is UIState.Success -> {
            CountryList(countryUiState.data, onCountryItemClick)
        }
    }
}

@Composable
fun CountryList(
    countryList: List<Country>,
    onCountryItemClick: (String) -> Unit,
) {
    LazyColumn {
        items(countryList, key = {
            it.name
        }) {
            CountryItem(it, onCountryItemClick)
        }
    }
}

@Composable
fun CountryItem(
    country: Country,
    onCountryItemClick: (String) -> Unit,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 5.dp, end = 12.dp, bottom = 5.dp)
            .clickable {
                onCountryItemClick.invoke(country.id)
            }
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(size = 5.dp)
            )
            .padding(10.dp),
        text = country.name,
        style = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    )
}
