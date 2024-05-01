package com.mattiasanfilippo.justquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnsweredQuestionDao {
    @Query("SELECT * FROM answeredQuestion WHERE quizId = :quizId")
    fun getAllByQuizId(quizId: Int): List<AnsweredQuestion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(answeredQuestion: AnsweredQuestion)

    @Query("DELETE FROM answeredQuestion WHERE quizId = :quizId")
    fun deleteAllByQuizId(quizId: Int)
}