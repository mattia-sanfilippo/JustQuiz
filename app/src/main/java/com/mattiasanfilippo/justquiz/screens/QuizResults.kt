package com.mattiasanfilippo.justquiz.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.NavController
import com.mattiasanfilippo.justquiz.R
import com.mattiasanfilippo.justquiz.ui.theme.AppTheme

@Composable
fun QuizResults(navController: NavController, innerPadding: PaddingValues, quizId: Int, totalQuestions: Int, correctAnswers: Int) {
    val percentage = (correctAnswers.toFloat() / totalQuestions.toFloat() * 100).toInt()

    fun onClickRetry() {
        navController.navigate("quiz/$quizId")
    }

    fun onClickGoToHome() {
        navController.navigate("home")
    }


    Content(innerPadding, correctAnswers, totalQuestions, percentage, ::onClickRetry, ::onClickGoToHome)
}

@Composable
fun Content(innerPadding: PaddingValues, correctAnswers: Int, totalQuestions: Int, percentage: Int, onClickRetry: () -> Unit, onClickGoToHome: () -> Unit) {
    Column(
        modifier = Modifier.padding(top = innerPadding.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
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