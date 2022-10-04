package com.example.kotlinfirestore.db

import android.content.ContentValues
import android.content.Context
import com.example.kotlinfirestore.model.Person
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class DatabaseHandler(context: Context) {
    init {
        if(FirebaseApp.getInstance() == null) FirebaseApp.initializeApp(context)
    }

    fun addPerson(person: Person): Boolean {
        val result = await(db.collection(PERSON_COLLEC_PATH).add(person))
        return result.id != null;
    }

    fun getPerson(id: String): Person? {
        val doc = await(db.collection(PERSON_COLLEC_PATH).document(id).get())

        return if(doc.exists()) doc.toObject<Person>()!! else null
    }

    companion object {
        private val PERSON_COLLEC_PATH = "people"
        private val db = Firebase.firestore
    }
}