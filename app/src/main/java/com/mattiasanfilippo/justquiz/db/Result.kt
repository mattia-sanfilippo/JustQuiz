package com.mattiasanfilippo.justquiz.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Result(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val quizId: Int,
    val correctAnswers: Int,
    val totalQuestions: Int
)
