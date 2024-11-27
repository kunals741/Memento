package com.kunal.memento.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_table")
data class Folder(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var folderName: String
)
