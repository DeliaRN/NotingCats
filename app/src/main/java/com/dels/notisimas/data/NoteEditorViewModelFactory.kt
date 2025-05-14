package com.dels.notisimas.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dels.notisimas.ui.NoteEditorViewModel

class NoteEditorViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteEditorViewModel::class.java)) {
            return NoteEditorViewModel(repository) as T
        }
        throw IllegalArgumentException("modelo desconocido")
    }
}