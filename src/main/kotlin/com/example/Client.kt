package com.example

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

@OptIn(InternalAPI::class)
suspend fun main() {
    val client = HttpClient()
    //LECTURA DE DATOS
    val file = File("./src/main/kotlin/com/example/TirantLoBlanc_Caps1_99.txt")
    val lector = FileReader(file)
    val buffer = BufferedReader(lector)
    var salir = false
    var line: String = buffer.readLine()

    var textoFiltrado=""
    while (!salir) {
        val paraulas = line.split(" ", ", ", ". ", ".", "·", "―", "'", ":", ";","?","!")
        for (paraula in paraulas) {
            if (paraula != "" && paraula.length >= 4) {
                textoFiltrado+="$paraula "
            }
        }
        try {
            line = buffer.readLine()
        } catch (e: Exception) {
            salir = true
        }
    }

    client.post("http://127.0.0.1:8080/post") {
        body = textoFiltrado
    }

    client.close()
}