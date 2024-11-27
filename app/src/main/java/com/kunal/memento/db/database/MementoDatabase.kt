package com.kunal.memento.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kunal.memento.db.dao.FolderDao
import com.kunal.memento.db.entity.Folder

@Database(entities = [Folder::class], version = 1)
abstract class MementoDatabase : RoomDatabase() {
    abstract fun folderDao(): FolderDao

    companion object {
        @Volatile
        private var INSTANCE: MementoDatabase? = null

        fun getDatabase(context: Context): MementoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MementoDatabase::class.java,
                    "Memento Database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}