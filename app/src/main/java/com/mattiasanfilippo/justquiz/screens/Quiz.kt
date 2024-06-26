package com.mattiasanfilippo.justquiz.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.mattiasanfilippo.justquiz.MainApplication
import com.mattiasanfilippo.justquiz.R
import com.mattiasanfilippo.justquiz.db.AnsweredQuestion
import com.mattiasanfilippo.justquiz.db.Result
import com.mattiasanfilippo.justquiz.model.Answer
import com.mattiasanfilippo.justquiz.model.CorrectAnswer
import com.mattiasanfilippo.justquiz.model.Question
import com.mattiasanfilippo.justquiz.model.QuestionList
import com.mattiasanfilippo.justquiz.ui.components.Answer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import kotlin.reflect.KFunction3

@Composable
fun Quiz(navController: NavController, innerPadding: PaddingValues, quizId: Int, onlyWrongQuestions: Boolean) {
    val context = LocalContext.current
    val inputStream: InputStream = context.resources.openRawResource(R.raw.questions)
    val json = inputStream.bufferedReader().use { it.readText() }

    val gson = Gson()
    val questionsList = gson.fromJson(json, QuestionList::class.java)

    var loading by remember { mutableStateOf(true) }
    var questions by remember { mutableStateOf<List<Question>>(emptyList()) }

    fun goToResults(quizId: Int, correctAnswers: List<CorrectAnswer>, totalQuestions: Int) {
        val result = Result(0, quizId, correctAnswers.size, totalQuestions)
        val db = MainApplication.database

        CoroutineScope(Dispatchers.IO).launch {
            // save the current result
            db.resultDao().insert(result)

            // delete all correct answered questions
            db.answeredQuestionDao().deleteAllByQuizId(quizId)

            // insert the correct answered questions
            correctAnswers.forEach {
                db.answeredQuestionDao().insert(AnsweredQuestion(quizId, it.questionId))
            }

            withContext(Dispatchers.Main) {
                navController.navigate("quizResults/$quizId/$totalQuestions/${correctAnswers.size}")
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = MainApplication.database
            val answeredQuestions = db.answeredQuestionDao().getAllByQuizId(quizId)
            val fetchedQuestions = if (onlyWrongQuestions) {
                questionsList.questions.find { it.quizId == quizId }?.questions?.filter { question -> answeredQuestions.none { it.questionId == question.id } }
            } else {
                questionsList.questions.find { it.quizId == quizId }?.questions
            }

            withContext(Dispatchers.Main) {
                questions = fetchedQuestions ?: emptyList()
                loading = false
            }
        }
    }

    if (loading) {
        CircularProgressIndicator(modifier = Modifier.testTag("LoadingIndicator"))
    } else {
        Content(innerPadding, quizId, questions, ::goToResults)
    }
}


@Composable
fun Content(innerPadding: PaddingValues, quizId: Int, questions: List<Question>, onClickGoToResults: KFunction3<Int, List<CorrectAnswer>, Int, Unit>) {

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var questionAnswered by remember { mutableStateOf(false) }
    var answerCorrect by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<Answer?>(null) }
    val correctAnswers by remember { mutableStateOf(mutableListOf<CorrectAnswer>())}

    fun onClickNext() {
        questionAnswered = false
        answerCorrect = false
        currentQuestionIndex++
    }

    if (currentQuestionIndex < questions.size) {
        val currentQuestion = questions[currentQuestionIndex]
        Column (
            Modifier.padding(top = innerPadding.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp).testTag("Quiz"),
        ) {
            QuestionNumber(currentQuestionIndex + 1, questions.size)
            Spacer(modifier = Modifier.height(8.dp))
            QuestionTitle(title = currentQuestion.question)
            Spacer(modifier = Modifier.height(24.dp))
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp),
            )
            {
                currentQuestion.answers.forEach { answer ->
                    Answer(answer = answer, onClick = {
                        questionAnswered = true
                        answerCorrect = answer.correct
                        selectedAnswer = answer

                        if (answer.correct) {
                            correctAnswers.add(CorrectAnswer(currentQuestion.id))
                        }

                    }, isCorrect = answerCorrect, isAnswered = questionAnswered, isSelected = selectedAnswer == answer)
                }
            }
            if (questionAnswered) {
                QuestionResultBox(answerCorrect, ::onClickNext)
            }
        }
    } else {
        FinalResultBox(onClickGoToResults = { onClickGoToResults(quizId, correctAnswers, questions.size) })
    }
}

@Composable
fun QuestionTitle (title: String) {
    Text(text = title, color = MaterialTheme.colorScheme.primary, fontSize = MaterialTheme.typography.titleLarge.fontSize)
}

@Composable
fun QuestionNumber (currentQuestionIndex: Int, totalQuestions: Int) {
    Text(text = "Question $currentQuestionIndex of $totalQuestions")
}

@Composable
fun FinalResultBox (onClickGoToResults: () -> Unit) {
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = "Quiz finished!", color = MaterialTheme.colorScheme.primary, fontSize = MaterialTheme.typography.titleLarge.fontSize)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onClickGoToResults) {
            Text(text = "Go to results")
        }
    }
}

@Composable
fun QuestionResultBox (isCorrect: Boolean, onClickNext: () -> Unit) {
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = if (isCorrect) "Your answer is correct!" else "Your answer is incorrect!", color = MaterialTheme.colorScheme.primary, fontSize = MaterialTheme.typography.titleLarge.fontSize)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onClickNext) {
            Text(text = "Next question")
        }
    }
}