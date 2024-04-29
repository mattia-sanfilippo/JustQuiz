package com.mattiasanfilippo.justquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.Gson
import com.mattiasanfilippo.justquiz.model.Quiz
import com.mattiasanfilippo.justquiz.model.QuizList
import com.mattiasanfilippo.justquiz.ui.components.QuizCard
import com.mattiasanfilippo.justquiz.ui.components.TopAppBar
import com.mattiasanfilippo.justquiz.ui.theme.AppTheme
import java.io.InputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inputStream: InputStream = resources.openRawResource(R.raw.quizzes)
        val json = inputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        val quizList = gson.fromJson(json, QuizList::class.java)

        setContent {
            AppTheme {
                Content(quizList.quizzes, ::onQuizCardClick)
            }
        }
    }

    private fun onQuizCardClick(quizId: Int) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("QUIZ_ID", quizId)
        startActivity(intent)
    }
}

@Preview
@Composable
fun PreviewMainActivity() {
    AppTheme {
        Content(
            quizzes = listOf(
                Quiz(1, "Quiz Name 1", 10),
                Quiz(2, "Quiz Name 2", 20),
                Quiz(3, "Quiz Name 3", 30),
            ),
            onQuizCardClick = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(quizzes: List<Quiz>, onQuizCardClick: (Int) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior) },
    ) {
        innerPadding ->
        ScrollContent(innerPadding, quizzes, onQuizCardClick)
    }
}

@Composable
fun ScrollContent(innerPadding: PaddingValues, quizzes: List<Quiz>, onQuizCardClick: (Int) -> Unit) {
    Column (modifier = Modifier.padding(innerPadding)) {
        Text(text = "Quizzes", color = MaterialTheme.colorScheme.primary, fontSize = MaterialTheme.typography.titleLarge.fontSize)
        LazyVerticalGrid(columns = GridCells.Adaptive(156.dp), contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp
        ), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(quizzes) { quiz ->
                QuizCard(quiz = quiz, onClick = { onQuizCardClick(quiz.id) })
            }
        }
    }
}

