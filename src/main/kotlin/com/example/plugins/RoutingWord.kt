package com.example.plugins

import com.example.Controlers.connectionFiles
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.rutaWords() {
    route("/words") {

        post() {
            val file= call.receive<String>()
            connectionFiles(file)
        }
    }
}