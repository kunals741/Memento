package com.kunal.memento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.memento.db.entity.Folders
import com.kunal.memento.db.entity.Note
import com.kunal.memento.repository.MementoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val folderDao = MainApplication.database.folderDao()
    private val repository = MementoRepository(folderDao)

    private val _folderList = MutableStateFlow<List<Folders>>(listOf())
    val folderList: StateFlow<List<Folders>> = _folderList

    private val _noteList = MutableStateFlow<List<Note>>(listOf())
    val noteList: StateFlow<List<Note>> = _noteList

    fun getAllFolders() = viewModelScope.launch {
        repository.allFolders?.collect {
            _folderList.value = it
        }
    }

    fun addNewFolder(folderName: String) = viewModelScope.launch {
        repository.insertFolder(folderName)
    }

    fun deleteFolderByID(folderID: Int) = viewModelScope.launch {
        repository.deleteFolderByID(folderID)
    }

    fun addNewNote(note: Note) = viewModelScope.launch {
        repository.addNoteToFolder(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun getNotesForFolderId(folderId: Int) = viewModelScope.launch {
        repository.getNotesForFolderId(folderId)?.collect {
            _noteList.value = it?.notes ?: emptyList()
        }
    }

    fun deleteNote(noteId: Int) = viewModelScope.launch {
        repository.deleteNoteByID(noteId)
    }
}