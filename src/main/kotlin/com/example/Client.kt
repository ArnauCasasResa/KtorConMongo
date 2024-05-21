package com.example

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*

@OptIn(InternalAPI::class)
suspend fun main() {
    val client = HttpClient()

    // Enviar un mensaje vacío al servidor
    client.post("http://127.0.0.1:8080/post")

    // Recibir el mensaje del servidor

    client.close()
}