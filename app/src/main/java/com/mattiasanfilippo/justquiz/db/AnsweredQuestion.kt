package com.mattiasanfilippo.justquiz.db

import androidx.room.Entity

@Entity(primaryKeys = ["quizId", "questionId"])
data class AnsweredQuestion(
    val quizId: Int,
    val questionId: Int,
)
