package com.dels.notisimas.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {
    @Query("SELECT * FROM notes")
    fun getAll(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getById(id: Int): LiveData<NoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: Int)

}