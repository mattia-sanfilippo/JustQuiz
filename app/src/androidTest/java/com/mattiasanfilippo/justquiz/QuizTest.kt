package com.mattiasanfilippo.justquiz

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.mattiasanfilippo.justquiz.screens.Quiz
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class QuizTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    @Test
    fun quizScreen_displaysLoadingIndicator() {
        val quizId = 1
        val onlyWrongQuestions = false

        composeTestRule.setContent {
            Quiz(navController = rememberNavController(), innerPadding = PaddingValues(), quizId = quizId, onlyWrongQuestions = onlyWrongQuestions)
        }

        composeTestRule.onNode(hasTestTag("LoadingIndicator")).assertIsDisplayed()
    }

    @Test
    fun quizScreen_displaysQuiz() {
        val quizId = 1
        val onlyWrongQuestions = false

        composeTestRule.setContent {
            Quiz(navController = rememberNavController(), innerPadding = PaddingValues(), quizId = quizId, onlyWrongQuestions = onlyWrongQuestions)
        }

        composeTestRule.onNode(hasTestTag("Quiz")).assertIsDisplayed()
    }


}