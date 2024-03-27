package com.mattiasanfilippo.justquiz.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mattiasanfilippo.justquiz.model.Quiz

@Composable
fun QuizCards(quizzes: List<Quiz>) {
    quizzes.forEach { quiz ->
        QuizCard(quiz = quiz)
    }
}

@Preview
@Composable
fun PreviewQuizCards() {
    QuizCards(
        quizzes = listOf(
            Quiz("Quiz Name 1", 10),
            Quiz("Quiz Name 2", 20),
            Quiz("Quiz Name 3", 30),
            Quiz("Quiz Name 4", 40),
            Quiz("Quiz Name 5", 50),
            Quiz("Quiz Name 6", 60),
            Quiz("Quiz Name 7", 70),
            Quiz("Quiz Name 8", 80),
            Quiz("Quiz Name 9", 90),
            Quiz("Quiz Name 10", 100),
        )
    )
}