package com.mattiasanfilippo.justquiz.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ScreenTitle(title: String) {
    Text(text = title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
}