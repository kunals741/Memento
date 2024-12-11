package com.kunal.memento.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kunal.memento.db.dao.MementoDao
import com.kunal.memento.db.entity.Folders
import com.kunal.memento.db.entity.Note

@Database(entities = [Folders::class, Note::class], version = 1)
abstract class MementoDatabase : RoomDatabase() {
    abstract fun folderDao(): MementoDao

    companion object {
        @Volatile
        private var INSTANCE: MementoDatabase? = null

        fun getDatabase(context: Context): MementoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MementoDatabase::class.java,
                    "Memento Database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}