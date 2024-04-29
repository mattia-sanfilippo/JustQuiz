package com.mattiasanfilippo.justquiz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattiasanfilippo.justquiz.model.Answer
import com.mattiasanfilippo.justquiz.ui.theme.AppTheme

@Composable
fun Answer(answer: Answer, onClick: () -> Unit, isCorrect: Boolean, isAnswered: Boolean, isSelected: Boolean) {
    val backgroundColor = when {
        isAnswered && isSelected -> {
            if (isCorrect) {
                Color.Green.copy(alpha = 0.2f)
            } else {
                Color.Red.copy(alpha = 0.2f)
            }
        }
        else -> MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp)
    }
    Surface (
        color = backgroundColor,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().height(56.dp),
        onClick = onClick,
        enabled = !isAnswered
    )
    {
        Column (
            modifier = Modifier.padding(16.dp).fillMaxHeight().wrapContentHeight(Alignment.CenterVertically)
        )
        {
            Text(text = answer.option, fontSize = MaterialTheme.typography.bodyLarge.fontSize, color = Color.Black)
        }
    }
}

@Preview
@Composable
fun PreviewAnswer() {
    AppTheme {
        Answer(Answer("Paris", true), onClick = { }, isCorrect = true, isAnswered = true, isSelected = false)
    }
}