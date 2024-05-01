package com.mattiasanfilippo.justquiz.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import com.google.gson.Gson
import com.mattiasanfilippo.justquiz.MainApplication
import com.mattiasanfilippo.justquiz.R
import com.mattiasanfilippo.justquiz.db.Result
import com.mattiasanfilippo.justquiz.model.Quiz
import com.mattiasanfilippo.justquiz.model.QuizList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun History(innerPadding: PaddingValues) {

    val context = LocalContext.current
    val inputStream: InputStream = context.resources.openRawResource(R.raw.quizzes)
    val json = inputStream.bufferedReader().use { it.readText() }

    val gson = Gson()
    val quizList = gson.fromJson(json, QuizList::class.java)

    val db = MainApplication.database

    val resultsWithQuiz = remember { mutableStateOf(listOf<Pair<Result, Quiz?>>()) }

    LaunchedEffect (key1 = Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val results = db.resultDao().getAll()

            resultsWithQuiz.value = results.map { result ->
                val quiz = quizList.quizzes.find { it.id == result.quizId }
                result to quiz
            }
        }
    }



    Column (modifier = Modifier.padding(innerPadding)) {
        LazyColumn {
            items(resultsWithQuiz.value) { (result, quiz) ->
                if (quiz != null) {
                    HistoryItem(quizName = quiz.name, date = result.timestamp, totalQuestions = result.totalQuestions, correctAnswers = result.correctAnswers)
                    Divider(color = Color.Gray, thickness = 0.5.dp)
                }
            }
        }
    }
}

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
