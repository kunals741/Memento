package com.kunal.memento.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class FolderWithNotes(
    @Embedded val folder: Folders,
    @Relation(
        parentColumn = "id",
        entityColumn = "folderId"
    )
    val notes: List<Note>
)
