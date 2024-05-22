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
    textoFiltrado.split(" ").forEach {

        print(it + " ")
        if ( contador >= 10 ) {
            contador = 0
            println()
        }
        contador++
    }
   /*
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
                textoFiltrado+="${paraula.toLowerCase()} "
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
    */
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