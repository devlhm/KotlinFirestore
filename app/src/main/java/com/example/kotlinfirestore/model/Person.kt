package com.example.kotlinfirestore.model

import androidx.annotation.NonNull
import org.intellij.lang.annotations.Pattern

data class Person @JvmOverloads constructor(
    val id: String?,
    val name: String,
    val address: String,
    val district: String,
    val cep: String
)
