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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattiasanfilippo.justquiz.ui.theme.AppTheme

class QuizResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val quizId = intent.getIntExtra("QUIZ_ID", 0)
        val correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)

        val percentage = (correctAnswers.toFloat() / totalQuestions.toFloat() * 100).toInt()

        setContent {
            AppTheme {
                Content(correctAnswers,  totalQuestions, percentage, { onClickRetry(quizId) }, ::onClickGoToHome)
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
fun Content(correctAnswers: Int, totalQuestions: Int, percentage: Int, onClickRetry: () -> Unit, onClickGoToHome: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (percentage >= 50) {
            PositiveResult(correctAnswers, totalQuestions, percentage)
        } else {
            NegativeResult(correctAnswers, totalQuestions, percentage)
        }
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

@Composable
fun PositiveResult(correctAnswers: Int, totalQuestions: Int, percentage: Int) {
    Icon(
        painter = painterResource(id = R.drawable.trophy_24),
        contentDescription = "Trophy Icon",
        tint = Color(0xFFF5BF00),
        modifier = Modifier
            .width(84.dp)
            .height(84.dp)
    )
    BigTitle("Congratulations!")
    Spacer(modifier = Modifier.height(16.dp))
    ResultText("You have completed the quiz, and scored $correctAnswers out of $totalQuestions. ($percentage%)")
}

@Composable
fun NegativeResult(correctAnswers: Int, totalQuestions: Int, percentage: Int) {
    Icon(
        painter = painterResource(id = R.drawable.replay_24),
        contentDescription = "Sad Icon",
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier
            .width(84.dp)
            .height(84.dp)
    )
    BigTitle("Try again!")
    Spacer(modifier = Modifier.height(16.dp))
    ResultText("You scored $correctAnswers out of $totalQuestions. ($percentage%), which is not enough to pass.")
}

@Composable
fun BigTitle(text: String) {
    Text(text = text, color = MaterialTheme.colorScheme.primary, fontSize = MaterialTheme.typography.titleLarge.fontSize)
}

@Composable
fun ResultText(text: String) {
    Text(text = text, color = MaterialTheme.colorScheme.primary, fontSize = MaterialTheme.typography.bodyLarge.fontSize, textAlign = TextAlign.Center)
}


@Preview
@Composable
fun PreviewPositiveResults() {
    AppTheme {
        PositiveResult(5, 10, 50)
    }
}