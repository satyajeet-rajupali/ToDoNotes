package com.satyajeet.todonotes.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.satyajeet.todonotes.adapter.NotesAdapter
import com.satyajeet.todonotes.NotesApp
import com.satyajeet.todonotes.utils.AppConstant
import com.satyajeet.todonotes.utils.PrefsConstant
import com.satyajeet.todonotes.R
import com.satyajeet.todonotes.clickListeners.ItemClickListener
import com.satyajeet.todonotes.db.Notes

class MyNotesActivity : AppCompatActivity() {

    private lateinit var full_name: String
    private lateinit var user_name: String
    private lateinit var sharedPreferences: SharedPreferences
    private var notes_list = ArrayList<Notes>()
    private lateinit var recyclerView: RecyclerView
    val TAG = "MyNotesActivity"
    private lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        bindingViews()
        setUpSharedPreference()
        getIntentValues()
        getDataFromDatabase()
        setUpRecyclerView()

        supportActionBar?.title = "Tasks"
        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
            startActivityForResult(intent, Companion.ADD_NOTES_CODE)
        }
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notes_list.addAll(notesDao.getAll())
    }


    private fun setUpRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailsActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }

        }
        val notesAdapter = NotesAdapter(notes_list, itemClickListener)
        recyclerView.adapter = notesAdapter
    }

    private fun getIntentValues() {
        val intent = intent
        full_name = intent.getStringExtra(AppConstant.FULL_NAME).toString()
        user_name = intent.getStringExtra(AppConstant.USER_NAME).toString()
        if (full_name.isEmpty()) {
            full_name = sharedPreferences.getString(PrefsConstant.FULL_NAME, "").toString()
        }

    }

    private fun setUpSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefsConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun bindingViews() {
        floatingActionButton = findViewById(R.id.fbutton)
        recyclerView = findViewById(R.id.rcv)
    }

    companion object {
        const val ADD_NOTES_CODE = 100
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTES_CODE) {
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)

            Log.d("Testing", "Image Path: " + imagePath)


            val notesApp = applicationContext as NotesApp
            val notesDao = notesApp.getNotesDb().notesDao()
            val note = Notes(title = title!!, description = description!!, imagePath = imagePath!!)
            Log.d(TAG, "Title: $title")
            Log.d(TAG, "Description: $description")

            notes_list.add(note)
            notesDao.insert(note)

            recyclerView.adapter?.notifyItemChanged(notes_list.size - 1)


        }
    }
}