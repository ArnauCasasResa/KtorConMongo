package com.example.plugins

import com.example.Controlers.connectionClients
import com.example.Controlers.pedirClientes
import com.example.Model.Client
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.rutaClientes() {
    route("/clients") {

        post() {
            val file= call.receive<String>()
            val client=file.split(" ")
            println(file)
            var cliente=Client(client[0],client[1],client[2],client[3].toInt())
            connectionClients(cliente)
        }
        get("/all"){
            val allUsers = pedirClientes()

            if (allUsers.isNotEmpty()) {
                call.respond(allUsers)
            } else {
                call.respondText("No hay nada!", status = HttpStatusCode.OK)
            }
        }
    }
}