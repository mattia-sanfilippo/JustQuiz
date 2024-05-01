package com.mattiasanfilippo.justquiz

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Quiz : Screen("quiz/{quizId}/{onlyWrongQuestions}")
    object QuizResults : Screen("quizResults/{quizId}/{totalQuestions}/{correctAnswers}")
}