package com.mattiasanfilippo.justquiz.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["quizId", "questionId"])
data class AnsweredQuestion(
    val quizId: Int,
    val questionId: Int,
)
