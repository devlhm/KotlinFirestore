package com.example.kotlinfirestore.db

import android.content.Context
import com.example.kotlinfirestore.model.Person
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
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

    fun addPerson(person: Person): Task<Void> {
        return db.collection(PERSON_COLLEC_PATH).document().set(person)
    }

    fun getPerson(id: String): Task<DocumentSnapshot> {
        return db.collection(PERSON_COLLEC_PATH).document(id).get()
    }

    fun getPeople(): Task<QuerySnapshot> {
        return db.collection(PERSON_COLLEC_PATH).get()
    }

    fun deletePerson(id: String): Task<Void> {
        return db.collection(PERSON_COLLEC_PATH).document(id).delete()
    }

    companion object {
        private const val PERSON_COLLEC_PATH = "people"
    }
}