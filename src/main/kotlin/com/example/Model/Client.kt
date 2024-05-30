package com.example.Model

import kotlinx.serialization.Serializable


@Serializable
class Client(
    val _id: Id?,
    val name: String,
    val urlPFP: String,
    val puntuacion: Int
)