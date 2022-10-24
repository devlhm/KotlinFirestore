package com.example.kotlinfirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfirestore.adapter.ListAdapter
import com.example.kotlinfirestore.db.DatabaseHandler
import com.example.kotlinfirestore.model.Person
import com.google.firebase.FirebaseApp
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {
    private var listAdapter: ListAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    private var personList = ArrayList<Person>()
    private val dbHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        var spinner : ProgressBar = findViewById(R.id.progressBar)
        spinner.visibility = View.VISIBLE

        Log.d("DEBUG", "init view")
        personList.clear()
        dbHandler.getPeople().addOnSuccessListener { result ->
            Log.d("DEBUG", "got registries")
            for (documentSnapshot in result) {
                val person = documentSnapshot.toObject(Person::class.java)
                personList.add(person)
            }

            listAdapter = ListAdapter(personList, this, this::deleteAdapter)
            linearLayoutManager = LinearLayoutManager(this)
            val personList = findViewById<RecyclerView>(R.id.person_list)
            personList.layoutManager = linearLayoutManager
            personList.adapter = listAdapter

            spinner.visibility = View.GONE
        }
    }

    private fun deleteAdapter(position: Int) {
        personList.removeAt(position)
        listAdapter!!.notifyItemRemoved(position)
    }
}