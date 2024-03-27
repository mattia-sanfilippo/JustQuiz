package com.mattiasanfilippo.justquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattiasanfilippo.justquiz.model.Quiz
import com.mattiasanfilippo.justquiz.ui.components.QuizCard
import com.mattiasanfilippo.justquiz.ui.components.TopAppBar
import com.mattiasanfilippo.justquiz.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Content()
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainActivity() {
    AppTheme {
        Content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior) },
    ) {
        innerPadding ->
        ScrollContent(innerPadding)
    }
}

@Composable
fun ScrollContent(innerPadding: PaddingValues) {
    val quizzes = listOf(
        Quiz("Quiz Name 1", 10),
        Quiz("Quiz Name 2", 20),
        Quiz("Quiz Name 3", 30),
        Quiz("Quiz Name 4", 40),
        Quiz("Quiz Name 5", 50),
    )

    LazyVerticalGrid(columns = GridCells.Adaptive(156.dp), contentPadding = PaddingValues(
        top = innerPadding.calculateTopPadding(),
        bottom = innerPadding.calculateBottomPadding(),
        start = 16.dp,
        end = 16.dp
    ), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(quizzes) { quiz ->
            QuizCard(quiz = quiz)
        }
    }
}
