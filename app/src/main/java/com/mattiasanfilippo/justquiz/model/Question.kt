package com.mattiasanfilippo.justquiz.model

data class Question(
    val id: Int,
    val question: String,
    val answers: List<Answer>
)
