package com.kunal.memento.db.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kunal.memento.db.entity.Note

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromNoteList(noteList: List<Note>?): String? {
        return noteList?.let {
            gson.toJson(it)
        }
    }

    @TypeConverter
    fun toNoteList(noteListString: String?): List<Note>? {
        return noteListString?.let {
            val type = object : TypeToken<List<Note>>() {}.type
            gson.fromJson(it, type)
        }
    }
}