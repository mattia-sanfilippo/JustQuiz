package com.mattiasanfilippo.justquiz.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattiasanfilippo.justquiz.R
import com.mattiasanfilippo.justquiz.model.Quiz
import com.mattiasanfilippo.justquiz.ui.theme.AppTheme

@Composable
fun QuizCard(quiz: Quiz) {
    Surface (
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(156.dp)
            .height(156.dp),
    )
    {
        Column (
            modifier = Modifier.padding(8.dp),
        ) {
            Icon(painter = painterResource(id = R.drawable.outline_flag_24), contentDescription = "Quiz Icon", tint = MaterialTheme.colorScheme.primary, modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterHorizontally)
                .width(84.dp)
                .height(84.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom)
             {
                Text(text = quiz.name, fontSize = MaterialTheme.typography.bodyLarge.fontSize, color = MaterialTheme.colorScheme.primary)
                Text(text = "${quiz.numberOfQuestions} questions", fontSize = MaterialTheme.typography.bodySmall.fontSize, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Preview
@Composable
fun PreviewQuizCard() {
    AppTheme {
        QuizCard(quiz = Quiz(1, "Quiz Name", 10))
    }
}