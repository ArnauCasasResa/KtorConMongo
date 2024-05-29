package com.example.Model

import kotlinx.serialization.Serializable


@Serializable
class Client(
    val name: String,
    val password: String,
    val urlPFP: String,
    val puntuacion: Int
)