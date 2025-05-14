package com.dels.notisimas.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dels.notisimas.data.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository

    val notes: LiveData<List<NoteEntity>>

    init {
        val dao = NoteDatabase.getInstance(application).noteDao()
        repository = NoteRepository(dao)
        notes = repository.getAll()
    }
}