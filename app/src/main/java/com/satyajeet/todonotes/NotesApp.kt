package com.satyajeet.todonotes

import android.app.Application
import com.satyajeet.todonotes.db.NotesDatabase

class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    fun getNotesDb(): NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}