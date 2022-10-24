package com.example.kotlinfirestore.model

import androidx.annotation.NonNull
import com.google.firebase.firestore.DocumentId
import org.intellij.lang.annotations.Pattern

data class Person constructor(
    @DocumentId
    val id: String
    var name: String = null,
    val address: String,
    val district: String,
    val cep: String
)
