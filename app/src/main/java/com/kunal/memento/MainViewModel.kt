package com.kunal.memento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.memento.db.entity.Folder
import com.kunal.memento.repository.MementoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val folderDao = MainApplication.database.folderDao()
    private val repository = MementoRepository(folderDao)

    private val _folderList = MutableStateFlow<List<Folder>>(listOf())
    val folderList: StateFlow<List<Folder>> = _folderList

    init {
        viewModelScope.launch {
            repository.allFolders.collect {
                _folderList.value = it
            }
        }
    }

    fun addNewFolder(folderName: String) = viewModelScope.launch {
        val folder = Folder(
            id = Random.toString(),
            folderName = folderName
        )
        repository.insertFolder(folder)
    }
}