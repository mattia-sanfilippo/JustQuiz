package com.mattiasanfilippo.justquiz.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Box {
        Column {
            Row {
                Text(text = quizName, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(2f))
                Text(text = convertTimestampToDate(date), color = MaterialTheme.colorScheme.secondary)
            }
            Text(text = "$correctAnswers out of $totalQuestions", color = MaterialTheme.colorScheme.secondary)
        }
    }
}
