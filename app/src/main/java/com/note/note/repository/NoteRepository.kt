package com.note.note.repository

import com.note.note.data.NoteDatabaseDao
import com.note.note.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDatabaseDao: NoteDatabaseDao
) {

    suspend fun addNote(note: Note) = noteDatabaseDao.insert(note = note)

    suspend fun updateNote(note: Note) = noteDatabaseDao.update(note = note)

    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note = note)

    suspend fun deleteAllNotes() = noteDatabaseDao.deleteAll()

    fun getAllNote(): Flow<List<Note>> = noteDatabaseDao.getNotes().flowOn(Dispatchers.IO).conflate()
}