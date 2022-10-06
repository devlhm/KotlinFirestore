package com.example.kotlinfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kotlinfirestore.db.DatabaseHandler
import com.example.kotlinfirestore.model.Person
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentReference
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val submitButton = findViewById<Button>(R.id.registerSubmit);
        submitButton.setOnClickListener { onSubmitButtonClick() };
    }

    private fun onSubmitButtonClick() {
        try {
            FirebaseApp.getInstance()
        } catch (e: IllegalStateException) {
            FirebaseApp.initializeApp(this)
        }

        val nameET = findViewById<EditText>(R.id.editTextName);
        val addressET = findViewById<EditText>(R.id.editTextAdress);
        val districtET = findViewById<EditText>(R.id.editTextDistrict);
        val cepET = findViewById<EditText>(R.id.editTextCEP);

        if (!validateForm(nameET, addressET, districtET, cepET)) return

        val dbHandler = DatabaseHandler(this);

        val person = Person(
            name = nameET.text.toString().trim(),
            address = addressET.text.toString().trim(),
            district = districtET.text.toString().trim(),
            cep = cepET.text.toString().trim()
        );

        val submitButton = findViewById<Button>(R.id.registerSubmit)
        Helpers.setBtnEnabled(submitButton, false)

        dbHandler.addPerson(person)
            .addOnSuccessListener { onPersonAdded() }
            .addOnFailureListener {
                Toast.makeText(this, "Erro no cadastro!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun onPersonAdded() {
        val submitButton = findViewById<Button>(R.id.registerSubmit)
        Helpers.setBtnEnabled(submitButton, true)
        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
    }

    private fun validateForm(
        nameET: EditText,
        addressET: EditText,
        districtET: EditText,
        cepET: EditText
    ): Boolean {
        if (nameET.text.trim().isNullOrEmpty()) {
            nameET.setError("Esse campo é necessário")
            return false
        }

        if (addressET.text.trim().isNullOrEmpty()) {
            addressET.setError("Esse campo é necessário")
            return false
        }

        if (districtET.text.trim().isNullOrEmpty()) {
            districtET.setError("Esse campo é necessário")
            return false
        }

        if (cepET.text.trim().isNullOrEmpty()) {
            cepET.setError("Esse campo é necessário")
            return false
        }

        if (!Regex("[0-9]{5}-[0-9]{3}").containsMatchIn(cepET.text.trim())) {
            cepET.setError("CEP inválido")
            return false
        }

        return true
    }
}