package com.kunal.memento.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "memento_table")
data class Folders(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var folderName: String
) : Parcelable
