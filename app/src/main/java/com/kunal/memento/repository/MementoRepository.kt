package com.kunal.memento.repository

import android.content.Context
import com.kunal.memento.db.dao.FolderDao
import com.kunal.memento.db.entity.Folder
import kotlinx.coroutines.flow.Flow

class MementoRepository(private val folderDao: FolderDao) {

    val allFolders: Flow<List<Folder>> = folderDao.getAllFolders()

    suspend fun insertFolder(folder: Folder) = folderDao.insertFolder(folder)

}