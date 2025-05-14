package com.dels.notisimas.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var content: String,
    //var iconId: Int,
    var colorHex: String
)


