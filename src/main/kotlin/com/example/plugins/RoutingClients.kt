package com.example.plugins

import com.example.Controlers.*
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
            var cliente=Client(null,client[0],client[1],client[2].toInt())
            connectionClients(cliente)
        }
        get("/all"){
            val allUsers = clientes()

            if (allUsers.isNotEmpty()) {
                call.respond(allUsers)
            } else {
                call.respondText("No hay nada!", status = HttpStatusCode.OK)
            }
        }
        post("/addPuntuacion"){
            val info = call.receive<Pair<String,Int>>() //"name password" + puntuacion
            val user = info.first.split(" ")
            val name = user[0]
            val pasword = user[1]
            val puntuacion = info.second

            val docChanges = editarPuntuacion(name, pasword, puntuacion)
            call.respondText("Documentos actualizados: $docChanges", status = HttpStatusCode.OK)
        }

    }
}