package com.vipint.newsapp.ui.home.uicomponets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainScreenItemSection(
    modifier: Modifier = Modifier,
    navController: NavController,
    routeName: String,
    title: String,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 5.dp, end = 12.dp, bottom = 5.dp)
            .clickable {
                navController.navigate(route = routeName)
            }
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(size = 5.dp)
            )
            .padding(10.dp),
        text = title,
        style = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        ),
    )
}