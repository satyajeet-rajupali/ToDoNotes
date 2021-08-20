package com.satyajeet.todonotes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satyajeet.todonotes.R
import com.androidnetworking.error.ANError

import org.json.JSONArray

import com.androidnetworking.interfaces.JSONArrayRequestListener

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.ParsedRequestListener
import com.satyajeet.todonotes.adapter.BlogsAdapter
import com.satyajeet.todonotes.model.JsonResponse


class BlogActivity : AppCompatActivity() {

    val TAG = "BlogActivity"
    lateinit var recyclerViewBlogs: RecyclerView
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)


        bindViews()
        getBlogs()

    }

    private fun setUpRecyclerView(response: JsonResponse?) {
        val blogsAdapter = BlogsAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogsAdapter
    }

    private fun getBlogs() {
        progressBar.visibility = View.VISIBLE
        AndroidNetworking.get("https://www.mocky.io/v2/5926ce9d11000096006ccb30")
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(JsonResponse::class.java, object : ParsedRequestListener<JsonResponse> {
                override fun onResponse(response: JsonResponse?) {
                    setUpRecyclerView(response)

                    progressBar.visibility = View.GONE
                }

                override fun onError(anError: ANError?) {
                    anError?.localizedMessage?.let { Log.d(TAG, it) }
                }

            })
    }


    private fun bindViews() {
        progressBar = findViewById(R.id.progressBar)
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs)
    }
}