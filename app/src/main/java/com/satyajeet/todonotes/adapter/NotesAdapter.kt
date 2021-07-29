package com.satyajeet.todonotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satyajeet.todonotes.R
import com.satyajeet.todonotes.clickListeners.ItemClickListener
import com.satyajeet.todonotes.db.Notes
import de.hdodenhof.circleimageview.CircleImageView

class NotesAdapter(private val notesArrayList : ArrayList<Notes>, private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_single_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        holder.title.text = notesArrayList[position].title
        holder.description.text = notesArrayList[position].description
        holder.checkBox.isChecked = notesArrayList[position].isTaskCompleted
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(notesArrayList[position])
        }
        Glide.with(holder.itemView).load(notesArrayList[position].imagePath).into(holder.imageView)

        holder.checkBox.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notesArrayList[position].isTaskCompleted = isChecked
                itemClickListener.onUpdate(notesArrayList[position])
            }

        })
    }

    override fun getItemCount(): Int {
        return notesArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.notes_title)
        val description: TextView = itemView.findViewById(R.id.notes_description)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxMarkStatus)
        val imageView: CircleImageView = itemView.findViewById(R.id.small_image)
    }

}