package com.example.kotlinfirestore.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfirestore.PersonActivity
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
        if(position % 2 == 0) holder.parentLayout.setBackgroundColor(Color.GRAY)
        else holder.parentLayout.setBackgroundColor(Color.WHITE)

        holder.name.setOnClickListener {
            val intent = Intent(ctx, PersonActivity::class.java)
            intent.putExtra("edit", true)
            intent.putExtra("person", person as java.io.Serializable)
            ctx.startActivity(intent)
        }

        holder.btn.setOnClickListener {
            AlertDialog.Builder(ctx)
                .setTitle("Deletar registro")
                .setMessage("Você tem certeza que deseja deletar esse registro?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sim"
                ) { dialog, whichButton ->
                    callbacks(position)
                    val dbHandler = DatabaseHandler(ctx)
                    dbHandler.deletePerson(person.id!!)
                }
                .setNegativeButton("Não", null).show()
        }
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tvAdpName)
        var btn: Button = view.findViewById(R.id.btnAdpDel)
        var parentLayout: LinearLayout = view.findViewById(R.id.parent_layout)
    }
}