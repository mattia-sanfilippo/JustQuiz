package com.mattiasanfilippo.justquiz.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mattiasanfilippo.justquiz.MainApplication
import com.mattiasanfilippo.justquiz.ui.components.ScreenTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Settings(innerPadding: PaddingValues) {
    val db = MainApplication.database
    val context = LocalContext.current

    fun onClickClearData() {
        CoroutineScope(Dispatchers.IO).launch {
            db.clearAllTables()
        }
        Toast.makeText(context, "Data cleared successfully", Toast.LENGTH_SHORT).show()
    }

    Column (
        modifier = Modifier.padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        ScreenTitle(title = "Settings")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onClickClearData() }) {
            Text("Clear Data")
        }
    }
}