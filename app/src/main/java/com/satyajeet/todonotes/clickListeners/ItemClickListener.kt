package com.satyajeet.todonotes.clickListeners

import com.satyajeet.todonotes.db.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}