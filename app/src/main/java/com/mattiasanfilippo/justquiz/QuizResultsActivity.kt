package com.mattiasanfilippo.justquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mattiasanfilippo.justquiz.ui.theme.AppTheme

class QuizResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val quizId = intent.getIntExtra("QUIZ_ID", 0)
        val correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)

        setContent {
            AppTheme {
                Content(quizId, correctAnswers,  totalQuestions, { onClickRetry(quizId) }, ::onClickGoToHome)
            }
        }
    }

    private fun onClickGoToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun onClickRetry(quizId: Int) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("QUIZ_ID", quizId)
        startActivity(intent)
    }
}

@Composable
fun Content(quizId: Int, correctAnswers: Int, totalQuestions: Int, onClickRetry: () -> Unit, onClickGoToHome: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Congratulations!")
        Spacer(modifier = Modifier.height(16.dp))
        Text("You have completed the quiz.")
        Spacer(modifier = Modifier.height(16.dp))
        Text("You got $correctAnswers out of $totalQuestions correct.")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onClickRetry) {
            Text("Retry")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onClickGoToHome) {
            Text("Go back to home")
        }
    }
}
