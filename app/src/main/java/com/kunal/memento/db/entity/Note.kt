package com.kunal.memento.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note_table",
   foreignKeys = [
        ForeignKey(
            entity = Folders::class,
            parentColumns = ["id"],
            childColumns = ["folderId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
    )
data class Note(
    @PrimaryKey
    val id: String,
    val folderId : String,
    var noteTitle: String,
    var noteText: String? = null,
    //var noteList: String? = null // todo will add support for list and other format later
) : Parcelable