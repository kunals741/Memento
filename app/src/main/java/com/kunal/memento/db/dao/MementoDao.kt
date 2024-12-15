package com.kunal.memento.db.dao

import androidx.room.Dao
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
    @Query("INSERT INTO memento_table (folderName) VALUES (:folderName)")
    suspend fun insertFolder(folderName: String)

    @Query("SELECT * FROM memento_table")
    fun getAllFolders(): Flow<List<Folders>>?

    @Query("DELETE FROM memento_table WHERE id = :folderId")
    suspend fun deleteFolderById(folderId: Int)

    @Insert
    suspend fun insertNote(note: Note)

    @Query("UPDATE note_table SET noteTitle = :title, noteText = :content WHERE id = :noteId")
    suspend fun updateNote(noteId: Int, title: String, content: String?)

    @Transaction
    @Query("SELECT * FROM memento_table WHERE id = :folderId")
    fun getNotesForFolderId(folderId: Int): Flow<FolderWithNotes?>?

    @Query("DELETE FROM note_table WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)

}