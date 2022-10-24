package com.example.kotlinfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kotlinfirestore.db.DatabaseHandler
import com.example.kotlinfirestore.model.Person
import com.google.firebase.FirebaseApp
import java.lang.IllegalStateException

class PersonActivity : AppCompatActivity() {
    private val dbHandler = DatabaseHandler(this)

    var nameET : EditText? = null
    var addressET : EditText? = null
    var districtET : EditText? = null
    var cepET : EditText? = null

    var personId: String? = null

    var edit : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)

        nameET = findViewById<EditText>(R.id.editTextName);
        addressET = findViewById<EditText>(R.id.editTextAdress);
        districtET = findViewById<EditText>(R.id.editTextDistrict);
        cepET = findViewById<EditText>(R.id.editTextCEP);

        edit = intent.getBooleanExtra("edit", false)

        if(edit as Boolean) {
            val person = intent.getSerializableExtra("person") as Person
            personId = person.id
            fillForm(person.name, person.address, person.district, person.cep)
        }

        val submitButton = findViewById<Button>(R.id.registerSubmit)
        submitButton.setOnClickListener { onSubmitButtonClick() }

        val cancelButton = findViewById<Button>(R.id.registerCancel)
        cancelButton.setOnClickListener { finish() }
    }

    private fun fillForm(name: String, address: String, district: String, cep: String) {
        nameET!!.setText(name)
        addressET!!.setText(address)
        districtET!!.setText(district)
        cepET!!.setText(cep)
    }

    private fun onSubmitButtonClick() {
        try {
            FirebaseApp.getInstance()
        } catch (e: IllegalStateException) {
            FirebaseApp.initializeApp(this)
        }

        if (!validateForm(nameET!!, addressET!!, districtET!!, cepET!!)) return

        val person = Person(
            name = nameET!!.text.toString().trim(),
            address = addressET!!.text.toString().trim(),
            district = districtET!!.text.toString().trim(),
            cep = cepET!!.text.toString().trim()
        );

        val submitButton = findViewById<Button>(R.id.registerSubmit)
        Helpers.setBtnEnabled(submitButton, false)

        if(edit as Boolean) {
            dbHandler.updatePerson(personId!!, person)
                .addOnSuccessListener {
                    onPersonAdded()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro na edição!", Toast.LENGTH_SHORT).show()
                }
        } else {
            dbHandler.addPerson(person)
                .addOnSuccessListener {
                    onPersonAdded()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro no cadastro!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun onPersonAdded() {
        val submitButton = findViewById<Button>(R.id.registerSubmit)
        Helpers.setBtnEnabled(submitButton, true)
        Toast.makeText(this, "Inserção realizada com sucesso!", Toast.LENGTH_SHORT).show()
        finish()
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