package com.example

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.regex.Pattern

@OptIn(InternalAPI::class)
suspend fun main() {



    val client = HttpClient()

    //SUBIDA DE DATOS

    val message=client.get("http://127.0.0.1:8080/clients/all")
    val textMessage=message.body<List<String>>()
    println(textMessage)

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
    val regexRomanos = "\\b$miles$centenas$decenas$unidades\\b".lowercase().toRegex()
    val regexPalabrasConNumeros = "\\b\\w*\\d+\\w*\\b".toRegex()

    //Aplicamos las expreciones regulares
    textoFinal = texto.replace(regexRomanos,"")
    textoFinal = textoFinal.replace(regexPalabrasConNumeros,"")

    //Eliminamos palabras repetidas
    val palabras = textoFinal.split("\\s+".toRegex())
    textoFinal = palabras.toCollection(LinkedHashSet()).joinToString(" ")
    textoFinal = textoFinal.replace("- ", " ")


    return textoFinal
}