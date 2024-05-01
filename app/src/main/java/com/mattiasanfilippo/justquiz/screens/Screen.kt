package com.mattiasanfilippo.justquiz.screens

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Home : Screen("home")
    object Quiz : Screen("quiz/{quizId}/{onlyWrongQuestions}")
    object QuizResults : Screen("quizResults/{quizId}/{totalQuestions}/{correctAnswers}")
    object History : Screen("history")
    object Settings : Screen("settings")
}