package com.satyajeet.todonotes.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.satyajeet.todonotes.Adapter.NotesAdapter
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

        floatingActionButton.setOnClickListener { setUpDialogBox() }
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notes_list.addAll(notesDao.getAll())
    }

    private fun setUpDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.dailog1, null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val button = view.findViewById<Button>(R.id.submit_button)

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()
        dialog.show()

        button.setOnClickListener {
            val notes_title = editTextTitle.text.toString()
            val notes_description = editTextDescription.text.toString()
            dialog.hide()
            if (notes_title.isNotEmpty() && notes_description.isNotEmpty()) {
                val notes = Notes(title = notes_title, description = notes_description)
                notes_list.add(notes)
                addNotesToDB(notes)
            } else {
                Toast.makeText(
                    this@MyNotesActivity,
                    "Title or Description is missing.",
                    Toast.LENGTH_SHORT
                ).show()


            }
        }
    }

    private fun addNotesToDB(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
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
}