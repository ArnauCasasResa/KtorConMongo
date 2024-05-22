package com.example

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun leerPalabras() {
    val file = File("./src/main/kotlin/com/example/TirantLoBlanc_Caps1_99.txt")
    val lector = FileReader(file)
    val buffer = BufferedReader(lector)
    var salir = false
    var line: String = buffer.readLine()
    val paraules = mutableListOf<String>()
    while (!salir) {
        print(line)
        val paraulas = line.split(" ", ", ", ". ", ".", "·", "―", "'",":",";")
        for (paraula in paraulas) {
            if (paraula != "" && paraula.length >= 4) {
                paraules.add(paraula)
            }
        }
        try {
            line = buffer.readLine()
        } catch (e: Exception) {
            salir = true
        }
    }

    println("Totes les paraules que es podran utilitzar son: ")
    for (paraula in paraules) {
        println(paraula)
    }
}

