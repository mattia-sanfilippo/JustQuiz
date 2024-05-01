package com.mattiasanfilippo.justquiz.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mattiasanfilippo.justquiz.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Settings(innerPadding: PaddingValues) {
    val db = MainApplication.database

    fun onClickClearData() {
        CoroutineScope(Dispatchers.IO).launch {
            db.clearAllTables()
        }
    }

    Column (
        modifier = Modifier.padding(top = innerPadding.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(onClick = { onClickClearData() }) {
            Text("Clear Data")
        }
    }
}