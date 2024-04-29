package com.mattiasanfilippo.justquiz.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Result::class, AnsweredQuestion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultDao
    abstract fun answeredQuestionDao(): AnsweredQuestionDao
}
