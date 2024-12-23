package com.kunal.memento.repository

import com.kunal.memento.db.dao.MementoDao
import com.kunal.memento.db.entity.FolderWithNotes
import com.kunal.memento.db.entity.Folders
import com.kunal.memento.db.entity.Note
import kotlinx.coroutines.flow.Flow

class MementoRepository(private val mementoDao: MementoDao) {

    val allFolders: Flow<List<Folders>>? = mementoDao.getAllFolders()

    suspend fun insertFolder(folderName: String) = mementoDao.insertFolder(folderName)

    suspend fun deleteFolderByID(folderId: Int) = mementoDao.deleteFolderById(folderId)

    suspend fun addNoteToFolder(note: Note) = mementoDao.insertNote(note)

    suspend fun updateNote(note: Note) = mementoDao.updateNote(
        noteId = note.id,
        title = note.noteTitle,
        content = note.noteText
    )

    fun getNotesForFolderId(folderId: Int): Flow<FolderWithNotes?>? = mementoDao.getNotesForFolderId(folderId)

    suspend fun deleteNoteByID(noteId: Int) = mementoDao.deleteNoteById(noteId)
}