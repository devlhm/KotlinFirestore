package com.example.kotlinfirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfirestore.adapter.ListAdapter
import com.example.kotlinfirestore.db.DatabaseHandler
import com.example.kotlinfirestore.model.Person
import com.google.firebase.FirebaseApp
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {
    var listAdapter: ListAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    val personList = ArrayList<Person>()
    var dbHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        findViewById<Button>(R.id.btnInsert).setOnClickListener {
            val intent = Intent(this, PersonActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        dbHandler.getPeople().addOnSuccessListener { result ->
            for (documentSnapshot in result) {
                val person = documentSnapshot.toObject(Person::class.java)
                personList.add(person)
            }
        }

        listAdapter = ListAdapter(personList, this, this::deleteAdapter)
        linearLayoutManager = LinearLayoutManager(this)
        var personList = findViewById<RecyclerView>(R.id.person_list)
        personList.layoutManager = linearLayoutManager
        personList.adapter = listAdapter
    }

    private fun deleteAdapter(position: Int) {
        personList.removeAt(position)
        listAdapter!!.notifyItemRemoved(position)
    }
}