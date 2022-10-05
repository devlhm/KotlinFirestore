package com.example.kotlinfirestore.db

import android.content.Context
import com.example.kotlinfirestore.model.Person
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.IllegalStateException

class DatabaseHandler(context: Context) {
    private var db: FirebaseFirestore

    init {
        try {
            FirebaseApp.getInstance()
        } catch (e: IllegalStateException) {
            FirebaseApp.initializeApp(context)
        }

        this.db = Firebase.firestore
    }

    fun addPerson(person: Person): Boolean {
        val result = await(db.collection(PERSON_COLLEC_PATH).add(person))
        return result.id != "";
    }

    fun getPerson(id: String): Person? {
        val doc = await(db.collection(PERSON_COLLEC_PATH).document(id).get())

        return if(doc.exists()) doc.toObject<Person>()!! else null
    }

    companion object {
        private const val PERSON_COLLEC_PATH = "people"
    }
}