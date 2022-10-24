package com.example.kotlinfirestore.model

import androidx.annotation.NonNull
import com.google.firebase.firestore.DocumentId
import org.intellij.lang.annotations.Pattern

data class Person(
    @DocumentId
    val id: String? = "",
    val name: String = "",
    val address: String = "",
    val district: String = "",
    val cep: String = ""
): java.io.Serializable
