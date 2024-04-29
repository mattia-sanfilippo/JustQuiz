package com.mattiasanfilippo.justquiz.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mattiasanfilippo.justquiz.model.Quiz

@Composable
fun QuizCards(quizzes: List<Quiz>) {
    quizzes.forEach { quiz ->
        QuizCard(quiz = quiz, onClick = { })
    }
}

@Preview
@Composable
fun PreviewQuizCards() {
    QuizCards(
        quizzes = listOf(
            Quiz(1, "Quiz Name 1", 10),
            Quiz(2, "Quiz Name 2", 20),
            Quiz(3, "Quiz Name 3", 30),
            Quiz(4, "Quiz Name 4", 40),
            Quiz(5, "Quiz Name 5", 50),
        )
    )
}