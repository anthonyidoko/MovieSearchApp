package com.example.moviesearch.ui.screens.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.moviesearch.R

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    message: String = "Searching ..."
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp),
            trackColor = MaterialTheme.colorScheme.tertiary,
            color = Color.White,
            strokeWidth = 7.dp
        )
        Text(
            text = message, modifier = Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = FontFamily(Font(R.font.playwrite_regular)),
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}