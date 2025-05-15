package com.dels.notisimas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dels.notisimas.data.NoteEntity
import com.dels.notisimas.data.NoteRepository
import kotlinx.coroutines.launch

class NoteCreatorViewModel (private val repository: NoteRepository) : ViewModel() {
    fun insertNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }
}