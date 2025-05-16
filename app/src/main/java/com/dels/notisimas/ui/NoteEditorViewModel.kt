package com.dels.notisimas.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dels.notisimas.data.*
import kotlinx.coroutines.launch

class NoteEditorViewModel(private val repository: NoteRepository) : ViewModel() {
    /**
     * Usa el repositorio para obtener y modifical los datos.
     * Le da a la UI la información. Mantiene los datos mientras la pantalla está viva.
     */

    val allNotes: LiveData<List<NoteEntity>> = repository.getAll()

    fun getNoteById(id: Int) : LiveData<NoteEntity?> = repository.getNoteById(id)

    fun insertNote(note: NoteEntity) = viewModelScope.launch { repository.insertNote(note) }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch { repository.updateNote(note) }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            val note = repository.getNoteById(id)
            if (note != null) {
                repository.deleteNote(id)
            }
        }
    }

    private val _selectedIconRes = MutableLiveData<Int>()
    val selectedIcon: LiveData<Int> = _selectedIconRes

    fun setSelectedIcon(resId: Int) {
        _selectedIconRes.value = resId
    }
}