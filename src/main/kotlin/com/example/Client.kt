package com.example

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.regex.Pattern

@OptIn(InternalAPI::class)
suspend fun main() {

    //LECTURA DE DATOS
    val file = File("./src/main/kotlin/com/example/TirantLoBlanc_Caps1_99.txt")
    val lector = FileReader(file)
    val buffer = BufferedReader(lector)
    var salir = false
    var line: String = buffer.readLine()

    var textoFiltrado=""
    while (!salir) {
        val paraulas = line.split(" ", ", ", ". ", ".", "·", "―", "'", ":", ";","?","!","\"")
        for (paraula in paraulas) {
            if (paraula != "" && paraula.length >= 4) {
                textoFiltrado+="${paraula.toLowerCase()} "
            }
        }
        try {
            line = buffer.readLine()
        } catch (e: Exception) {
            salir = true
        }
    }

    var contador = 0
    textoFiltrado = tratarTexto(textoFiltrado)
    /**
    textoFiltrado.split(" ").forEach {

        print(it + " ")
        if ( contador >= 10 ) {
            contador = 0
            println()
        }
        contador++
    }
    */

    val client = HttpClient()

    //SUBIDA DE DATOS

    client.post("http://127.0.0.1:8080/words") {
        body = textoFiltrado
    }
    val usuario = "Arnau Arnau1234567890 https://www.google.com/url?sa=i&url=https%3A%2F%2Fpixabay.com%2Fes%2Fimages%2Fsearch%2Fimagen%2F&psig=AOvVaw38E7d9_Dte8PwL7ZBXCnEI&ust=1716465087520000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCKiOxfCYoYYDFQAAAAAdAAAAABAE 0"


    client.post("http://127.0.0.1:8080/clients") {
        body = usuario
    }

    client.close()

}

fun tratarTexto(texto: String): String {
    var textoFinal = texto

    // Partes de la expresión regular para números romanos
    val miles = "M{0,4}"
    val centenas = "(CM|CD|D?C{0,4})"
    val decenas = "(XC|XL|L?X{0,4})"
    val unidades = "(IX|IV|V?I{0,4})"

    // Expresión regular completa uniendo las partes
    val regexRomanos = "\\b$miles$centenas$decenas$unidades\\b".toLowerCase().toRegex()
    val regexPalabrasConNumeros = "\\b\\w*\\d+\\w*\\b".toRegex()

    //Aplicamos las expreciones regulares
    textoFinal = texto.replace(regexRomanos,"")
    textoFinal = textoFinal.replace(regexPalabrasConNumeros,"")

    //Eliminamos palabras repetidas
    val palabras = textoFinal.split("\\s+".toRegex())
    textoFinal = palabras.toCollection(LinkedHashSet()).joinToString(" ")

    return textoFinal
}