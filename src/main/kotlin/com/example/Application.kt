package com.example

import com.example.plugins.rutaClientes
import com.example.plugins.rutaPalabras
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    rutaPalabras()
    rutaClientes()
}
