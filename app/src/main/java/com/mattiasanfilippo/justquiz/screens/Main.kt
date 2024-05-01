package com.mattiasanfilippo.justquiz.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mattiasanfilippo.justquiz.ui.components.BottomNavigationBar
import com.mattiasanfilippo.justquiz.ui.components.TopAppBar
import com.mattiasanfilippo.justquiz.ui.components.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior) },
        bottomBar = {
            if (currentRoute in items.map { it.route }) {
                BottomNavigationBar(navController) }
            }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) { Home(navController, innerPadding) }
            composable(Screen.History.route) { History(innerPadding) }
            composable(Screen.Settings.route) { Settings(innerPadding) }

            composable(
                Screen.Quiz.route,
                arguments = listOf(
                    navArgument("quizId") { type = NavType.IntType },
                    navArgument("onlyWrongQuestions") { type = NavType.BoolType }
                )) { backStackEntry ->
                val quizId = backStackEntry.arguments?.getInt("quizId")
                val onlyWrongQuestions = backStackEntry.arguments?.getBoolean("onlyWrongQuestions")

                Quiz(navController, innerPadding, quizId = quizId!!, onlyWrongQuestions = onlyWrongQuestions!!)
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

                QuizResults(navController, innerPadding, quizId = quizId!!, totalQuestions = totalQuestions!!, correctAnswers = correctAnswers!!)
            }

        }
    }
}