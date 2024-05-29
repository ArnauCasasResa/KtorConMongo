package com.example.plugins

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import org.bson.Document
import org.bson.types.Binary
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun Application.rutingApi() {
    routing {
       rutaClientes()
        rutaWords()
    }
}
