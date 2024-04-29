package com.mattiasanfilippo.justquiz

import android.app.Application
import androidx.room.Room
import com.mattiasanfilippo.justquiz.db.AppDatabase

class MainApplication : Application() {
    companion object {
        lateinit var database: AppDatabase private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "justquiz-db").build()
    }
}