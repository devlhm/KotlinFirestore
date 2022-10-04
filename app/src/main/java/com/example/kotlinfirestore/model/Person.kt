package com.example.kotlinfirestore.model

data class Person(
    val id: String? = null,
    val name: String,
    val address: String,
    val district: String,
    val cep: String
)
