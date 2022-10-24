package com.example.kotlinfirestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfirestore.R
import com.example.kotlinfirestore.db.DatabaseHandler
import com.example.kotlinfirestore.model.Person

class ListAdapter (peopleList: List<Person>, internal var ctx: Context, private val callbacks: (Int) -> Unit): RecyclerView.Adapter<ListAdapter.ViewHolder>(){
    internal var peopleList: List<Person> = ArrayList()

    init {
        this.peopleList = peopleList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = peopleList[position]

        holder.name.text = person.name
        holder.btn.setOnClickListener {
            val dbHandler = DatabaseHandler(ctx)
            dbHandler.deletePerson(person.id!!)
            callbacks(position)
        }
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }



    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tvAdpName)
        var btn: Button = view.findViewById(R.id.btnAdpDel)
    }
}