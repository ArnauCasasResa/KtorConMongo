package com.example.plugins

import com.example.Controlers.*
import com.example.Model.Client
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

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
                val jsonList = Json.encodeToString(ListSerializer(Client.serializer()), allUsers as List<Client>)
                call.respond(jsonList)
            } else {
                call.respondText("No hay nada!", status = HttpStatusCode.OK)
            }
        }
        post("/addPuntuacion"){
            val info = call.receive<Pair<String,Int>>() //"name" + puntuacion
            val name = info.first
            val puntuacion = info.second

            val docChanges = editarPuntuacion(name, puntuacion)
            call.respondText("Documentos actualizados: $docChanges", status = HttpStatusCode.OK)
        }

    }
}