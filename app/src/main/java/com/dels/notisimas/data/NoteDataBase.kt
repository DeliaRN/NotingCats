package com.dels.notisimas.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [NoteEntity::class], version = 2)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO

    companion object {
        @Volatile private var INSTANCE: NoteDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE notes ADD COLUMN iconId INTEGER NOT NULL DEFAULT 2131165406"
                )
            }
        }

        fun getInstance(context: Context): NoteDatabase =
            INSTANCE ?: synchronized(NoteDatabase::class.java) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
    }
}