package com.kunal.memento.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "memento_table")
data class Folders(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var folderName: String
) : Parcelable
