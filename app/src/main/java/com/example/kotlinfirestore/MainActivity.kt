package com.example.kotlinfirestore

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val submitButton = findViewById<Button>(R.id.registerSubmit);
        submitButton.setOnClickListener { onSubmitButtonClick() };
    }

    private fun onSubmitButtonClick() {
        FirebaseApp.initializeApp(this)

        val name = findViewById<EditText>(R.id.editTextName).text.toString();
        val address = findViewById<EditText>(R.id.editTextAdress).text.toString();
        val district = findViewById<EditText>(R.id.editTextDistrict).text.toString();
        val cep = findViewById<EditText>(R.id.editTextCEP).text.toString();

        val db = Firebase.firestore;

        if(!validateFields(arrayOf(name, address, district, cep))) {
            return;
        }

        val person = hashMapOf(
            "name" to name,
            "address" to address,
            "district" to district,
            "cep" to cep
        )

        db.collection("people")
            .add(person)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    public fun validateFields(fields: Array<String>): Boolean {
        fields.forEach {
            if(it == "") {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG);
                return false;
            }
        }

        return true;
    }
}