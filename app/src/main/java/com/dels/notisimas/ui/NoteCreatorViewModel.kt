package com.dels.notisimas.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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