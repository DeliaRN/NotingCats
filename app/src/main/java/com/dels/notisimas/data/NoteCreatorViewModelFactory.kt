package com.dels.notisimas.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dels.notisimas.ui.NoteCreatorViewModel

class NoteCreatorViewModelFactory (private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteCreatorViewModel::class.java)) {
            return NoteCreatorViewModel(repository) as T
        }
        throw IllegalArgumentException("modelo desconocido")
    }
}