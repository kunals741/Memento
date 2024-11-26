package com.kunal.memento

import androidx.lifecycle.ViewModel
import com.kunal.memento.model.FolderModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class MainViewModel : ViewModel() {


    private val _folderList = MutableStateFlow<ArrayList<FolderModel>>(arrayListOf())
    val folderList: StateFlow<ArrayList<FolderModel>> = _folderList

    fun addNewFolder(folderName: String) {
        val folder = FolderModel(
            id = Random.toString(),
            folderName = folderName
        )
        val updatedList = ArrayList(_folderList.value)
        updatedList.add(folder)
        _folderList.value = updatedList
    }
}