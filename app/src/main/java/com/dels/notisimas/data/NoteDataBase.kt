package com.dels.notisimas.data

import android.content.Context
import androidx.room.*

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO

    companion object {
        @Volatile private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase =
            INSTANCE ?: synchronized(NoteDatabase::class.java) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build().also { INSTANCE = it }
            }
    }
}