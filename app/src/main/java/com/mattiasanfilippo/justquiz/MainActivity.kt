package com.mattiasanfilippo.justquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.mattiasanfilippo.justquiz.model.Quiz
import com.mattiasanfilippo.justquiz.model.QuizList
import com.mattiasanfilippo.justquiz.ui.components.QuizCard
import com.mattiasanfilippo.justquiz.ui.components.TopAppBar
import com.mattiasanfilippo.justquiz.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inputStream: InputStream = resources.openRawResource(R.raw.quizzes)
        val json = inputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        val quizList = gson.fromJson(json, QuizList::class.java)

        val db = MainApplication.database
        val completedQuizzes = mutableSetOf<Quiz>()

        CoroutineScope(Dispatchers.IO).launch {
            val results = db.resultDao().getAll()
            results.forEach() { result ->
                val quiz = quizList.quizzes.find { it.id == result.quizId }
                if (quiz != null) {
                    completedQuizzes.add(quiz)
                }
            }

        }


        setContent {
            AppTheme {
                ContentWithDialog(quizList.quizzes, completedQuizzes.toList(), ::onQuizCardClick, ::onConfirmRetryQuiz, ::onConfirmWithoutCorrectAnswersQuiz)
            }
        }
    }

    private fun onQuizCardClick(quizId: Int) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("QUIZ_ID", quizId)
        startActivity(intent)
    }
    
    private fun onConfirmRetryQuiz(quizId: Int) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("QUIZ_ID", quizId)
        startActivity(intent)
    }

    private fun onConfirmWithoutCorrectAnswersQuiz(quizId: Int) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("QUIZ_ID", quizId)
        intent.putExtra("ONLY_WRONG_QUESTIONS", true)
        startActivity(intent)
    }
}

@Composable
fun ContentWithDialog (quizzes: List<Quiz>, completedQuizzes: List<Quiz>, onQuizCardClick: (Int) -> Unit, onConfirmRetryQuiz: (Int) -> Unit, onRetryWithoutCorrectAnswersQuiz: (Int) -> Unit){
    val showDialog = remember { mutableStateOf(false) }
    val selectedQuizId = remember { mutableIntStateOf(0) }

    fun onRetryQuizCardClick(quizId: Int) {
        selectedQuizId.intValue = quizId
        showDialog.value = true
    }

    fun onConfirmRetryQuizClick() {
        showDialog.value = false
        onConfirmRetryQuiz(selectedQuizId.intValue)
    }

    fun onRetryWithoutCorrectAnswersQuizClick() {
        showDialog.value = false
        onRetryWithoutCorrectAnswersQuiz(selectedQuizId.intValue)
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Retry quiz") },
            text = { Text("Do you want to retry the quiz with only the questions you answered incorrectly?") },
            confirmButton = {
                TextButton(onClick = { onRetryWithoutCorrectAnswersQuizClick() }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { onConfirmRetryQuizClick() }) {
                    Text("No")
                }
            }
        )
    }
    Content(quizzes, completedQuizzes, onQuizCardClick, ::onRetryQuizCardClick)
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
            completedQuizzes = listOf(
                Quiz(4, "Quiz Name 4", 40),
                Quiz(5, "Quiz Name 5", 50),
                Quiz(6, "Quiz Name 6", 60),
            ),
            onQuizCardClick = { },
            onRetryQuizCardClick = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(quizzes: List<Quiz>, completedQuizzes: List<Quiz>, onQuizCardClick: (Int) -> Unit, onRetryQuizCardClick: (Int) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior) },
    ) {
        innerPadding ->
        ScrollContent(innerPadding, quizzes, completedQuizzes, onQuizCardClick, onRetryQuizCardClick)
    }
}

@Composable
fun ScrollContent(innerPadding: PaddingValues, quizzes: List<Quiz>, completedQuizzes: List<Quiz>, onQuizCardClick: (Int) -> Unit, onRetryQuizCardClick: (Int) -> Unit) {
    Column (modifier = Modifier.padding(innerPadding)) {
        CompletedQuizzesGrid(completedQuizzes, onRetryQuizCardClick)
        Spacer(modifier = Modifier.height(16.dp))
        NewQuizGrid(quizzes, onQuizCardClick)
    }
}

@Composable
fun NewQuizGrid(quizzes: List<Quiz>, onQuizCardClick: (Int) -> Unit) {
    Text(text = "All quizzes", color = MaterialTheme.colorScheme.primary, fontSize = MaterialTheme.typography.titleLarge.fontSize)
    LazyVerticalGrid(columns = GridCells.Adaptive(156.dp), contentPadding = PaddingValues(
        start = 16.dp,
        end = 16.dp
    ), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(quizzes) { quiz ->
            QuizCard(quiz = quiz, onClick = { onQuizCardClick(quiz.id) })
        }
    }
}

@Composable
fun CompletedQuizzesGrid(completedQuizzes: List<Quiz>, onQuizCardClick: (Int) -> Unit) {
    Text(text = "Practice again", color = MaterialTheme.colorScheme.primary, fontSize = MaterialTheme.typography.titleLarge.fontSize)
    LazyVerticalGrid(columns = GridCells.Adaptive(156.dp), contentPadding = PaddingValues(
        start = 16.dp,
        end = 16.dp
    ), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(completedQuizzes) { quiz ->
            QuizCard(quiz = quiz, onClick = { onQuizCardClick(quiz.id) })
        }
    }
}
