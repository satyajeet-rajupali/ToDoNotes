package com.satyajeet.todonotes.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.satyajeet.todonotes.NotesApp

class MyWorker(val context: Context, private val workerParameters: WorkerParameters): Worker(context, workerParameters) {

    override fun doWork(): Result {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.deleteNotes(true)
        return Result.success()
    }


}