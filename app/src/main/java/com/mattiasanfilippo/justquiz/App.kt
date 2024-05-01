package com.mattiasanfilippo.justquiz

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mattiasanfilippo.justquiz.screens.Home
import com.mattiasanfilippo.justquiz.screens.Quiz
import com.mattiasanfilippo.justquiz.screens.QuizResults
import com.mattiasanfilippo.justquiz.screens.Screen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route ) {
        composable(Screen.Home.route) { Home(navController) }
        composable(
            Screen.Quiz.route,
            arguments = listOf(
                navArgument("quizId") { type = NavType.IntType },
                navArgument("onlyWrongQuestions") { type = NavType.BoolType }
            )) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getInt("quizId")
            val onlyWrongQuestions = backStackEntry.arguments?.getBoolean("onlyWrongQuestions")

            Quiz(navController, quizId = quizId!!, onlyWrongQuestions = onlyWrongQuestions!!)
        }
        composable(
            Screen.QuizResults.route,
            arguments = listOf(
                navArgument("quizId") { type = NavType.IntType },
                navArgument("totalQuestions") { type = NavType.IntType },
                navArgument("correctAnswers") { type = NavType.IntType }
            )) { backStackEntry ->
            val quizId = backStackEntry.arguments?.getInt("quizId")
            val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions")
            val correctAnswers = backStackEntry.arguments?.getInt("correctAnswers")

            QuizResults(navController, quizId = quizId!!, totalQuestions = totalQuestions!!, correctAnswers = correctAnswers!!)
        }
    }
}
