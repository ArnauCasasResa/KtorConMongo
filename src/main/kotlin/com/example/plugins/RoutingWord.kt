package com.example.plugins

import com.example.Controlers.connectionFiles
import com.example.Controlers.grupoRima
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.random.Random

fun Route.rutaWords() {
    route("/words") {

        post() {
            val file= call.receive<String>()
            connectionFiles(file)
        }
        get() {
            val words = grupoRima()

            if (words.isNotEmpty()) {
                val jsonList = Json.encodeToString(ListSerializer(String.serializer()), words as List<String>)
                call.respond(jsonList)
            } else {
                call.respondText("No hay nada!", status = HttpStatusCode.OK)
            }
        }

        get("/Sonometro"){
            val puntuacion = Random.nextInt(1, 101)
            call.respond(puntuacion)
        }
    }
}