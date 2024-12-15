package com.kunal.memento

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kunal.memento.db.database.MementoDatabase

class MainApplication : Application() {
    companion object {
        lateinit var database: MementoDatabase
            private set
        lateinit var auth : FirebaseAuth
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = MementoDatabase.getDatabase(this)
        auth = Firebase.auth
    }
}