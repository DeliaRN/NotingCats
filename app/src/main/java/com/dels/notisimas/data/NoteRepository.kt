package com.dels.notisimas.data

import android.util.Log


class NoteRepository(private val dao: NoteDAO) {

    /**
     * Puente de datos: Conecta el DAO con la app.
     */

    fun getAll() = dao.getAll()

    fun getNoteById(id: Int) = dao.getById(id)

    suspend fun insertNote(note: NoteEntity) {
        Log.d("EEEEEE NOTEREPOSITORY AQUÍ", "INSERTANDO NOTA: $note")
        dao.insert(note)
    }

    suspend fun updateNote(updatedNote: NoteEntity) = dao.update(updatedNote)

    suspend fun deleteNote(id: Int) = dao.delete(id)

}