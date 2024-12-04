package com.kunal.memento.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kunal.memento.db.entity.FolderWithNotes
import com.kunal.memento.db.entity.Folders
import com.kunal.memento.db.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface MementoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: Folders)

    @Query("SELECT * FROM memento_table")
    fun getAllFolders(): Flow<List<Folders>>?

    @Query("DELETE FROM memento_table WHERE id = :folderId")
    suspend fun deleteFolderById(folderId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("UPDATE note_table SET noteTitle = :title, noteText = :content WHERE id = :noteId")
    suspend fun updateNote(noteId: String, title: String, content: String?)

    @Transaction
    @Query("SELECT * FROM memento_table WHERE id = :folderId")
    fun getNotesForFolderId(folderId: String): Flow<FolderWithNotes?>?

    @Query("DELETE FROM note_table WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: String)

}