package com.satyajeet.todonotes.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satyajeet.todonotes.R
import com.satyajeet.todonotes.model.Data

class BlogsAdapter(val list: List<Data>) : RecyclerView.Adapter<BlogsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blogs_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTv.text = list[position].title
        holder.descriptionTv.text = list[position].description
        Glide.with(holder.itemView).load(list[position].img_url).into(holder.blog_image)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.textViewTitle)
        val descriptionTv: TextView = itemView.findViewById(R.id.textViewDescription)
        val blog_image: ImageView = itemView.findViewById(R.id.imageView)
    }
}