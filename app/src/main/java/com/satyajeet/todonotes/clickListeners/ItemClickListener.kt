package com.satyajeet.todonotes.clickListeners

import com.satyajeet.todonotes.model.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
}