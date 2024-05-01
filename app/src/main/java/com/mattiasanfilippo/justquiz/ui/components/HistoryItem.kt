package com.mattiasanfilippo.justquiz.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertTimestampToDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
    return format.format(date)
}

@Composable
fun HistoryItem(quizName: String, date: Long, totalQuestions: Int, correctAnswers: Int) {
    Surface (
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .height(84.dp),
    )  {
        Column (verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(16.dp)) {
            Row {
                Text(text = quizName, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(2f))
                Text(text = convertTimestampToDate(date), color = MaterialTheme.colorScheme.secondary)
            }
            Text(text = "$correctAnswers out of $totalQuestions", color = MaterialTheme.colorScheme.secondary)
        }
    }
}
