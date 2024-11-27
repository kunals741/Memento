package com.kunal.memento.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kunal.memento.db.entity.Folder
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Insert
    suspend fun insertFolder(folder: Folder)

    @Query("SELECT * FROM folder_table")
    fun getAllFolders(): Flow<List<Folder>>

    //update and delete method later:
//    // Update an existing folder
//    @Update
//    suspend fun updateFolder(folder: Folder)
//
//    // Delete a single folder
//    @Delete
//    suspend fun deleteFolder(folder: Folder)
//
//    // Delete folder by ID
//    @Query("DELETE FROM folder_table WHERE id = :folderId")
//    suspend fun deleteFolderById(folderId: Long)

}