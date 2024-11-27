package com.kunal.memento

import android.app.Application
import com.kunal.memento.db.database.MementoDatabase

class MainApplication : Application() {
    companion object {
        lateinit var database: MementoDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = MementoDatabase.getDatabase(this)
    }
}