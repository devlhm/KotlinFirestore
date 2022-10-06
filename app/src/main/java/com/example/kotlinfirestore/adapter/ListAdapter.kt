package com.example.kotlinfirestore.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfirestore.model.Person

class ListAdapter (peopleList: List<Person>, internal var ctx: Context, private val callbacks: (Int) -> Unit): RecyclerView.Adapter<ListAdapter.ViewHolder>(){
    internal var peopleList: List<Person> = ArrayList<Person>()

    init {
        this.peopleList = peopleList
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}