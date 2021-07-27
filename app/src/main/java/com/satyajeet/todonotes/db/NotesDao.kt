package com.satyajeet.todonotes.db


import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

// Data Access Object - DAO
@Dao
interface NotesDao {

    @Query("SELECT * from notesData")
    fun getAll(): List<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Update
    fun updateNotes(notes: Notes)

    @Delete
    fun delete(notes: Notes)
}