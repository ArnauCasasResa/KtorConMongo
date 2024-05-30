package com.example.Model

import kotlinx.serialization.Serializable

@Serializable
data class Fichero (
    val _id:Id,
    val file: String
)