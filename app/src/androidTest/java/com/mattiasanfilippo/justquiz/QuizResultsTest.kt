package com.mattiasanfilippo.justquiz

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mattiasanfilippo.justquiz.screens.QuizResults
import com.mattiasanfilippo.justquiz.screens.Screen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizResultsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    @Test
    fun quizResultsScreen_displaysPositiveResult() {
        val quizId = 1
        val totalQuestions = 10
        val correctAnswers = 7

        composeTestRule.setContent {
            QuizResults(
                navController = rememberNavController(),
                innerPadding = PaddingValues(),
                quizId = quizId,
                totalQuestions = totalQuestions,
                correctAnswers = correctAnswers
            )
        }

        composeTestRule.onNode(hasTestTag("PositiveResult")).assertIsDisplayed()
    }

    @Test
    fun quizResultsScreen_displaysNegativeResult() {
        val quizId = 1
        val totalQuestions = 10
        val correctAnswers = 3

        composeTestRule.setContent {
            QuizResults(
                navController = rememberNavController(),
                innerPadding = PaddingValues(),
                quizId = quizId,
                totalQuestions = totalQuestions,
                correctAnswers = correctAnswers
            )
        }

        composeTestRule.onNode(hasTestTag("NegativeResult")).assertIsDisplayed()
    }
}