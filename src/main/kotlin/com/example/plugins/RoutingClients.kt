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

fun Application.rutaClientes() {
    routing {
        post("/clients") {
            val file= call.receive<String>()
            val client=file.split(" ")
            println(file)
            var cliente=Client(client[0],client[1],client[2],client[3].toInt())
            connectionClients(cliente)
        }
    }
}
fun connectionClients(client: Client) {
    val username = "arnaucasas7e7"
    // Replace the placeholders with your credentials and hostname
    val connectionString = "mongodb+srv://$username:$username@batalla.fspyxte.mongodb.net/?retryWrites=true&w=majority&appName=Batalla"


    val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()

    val mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(connectionString))
        .serverApi(serverApi)
        .build()

    // Create a new client and connect to the server
    MongoClients.create(mongoClientSettings).use { mongoClient ->
        val database = mongoClient.getDatabase("batalla")
        val collection = database.getCollection("clientes")

        runBlocking {
            database.runCommand(Document("ping", 1))
        }
        println("Pinged your deployment. You successfully connected to MongoDB!")

        subirCliente(collection, client)

    }
}


fun subirCliente(collection: MongoCollection<Document>, client: Client) {
    val clientJson = Json.encodeToString(client)
    val clientDocument = Document.parse(clientJson)
    collection.insertOne(clientDocument)
    println("Inserted client into the 'clients' collection.")
}

@Serializable
class Client(val name: String, val password: String, val urlPFP: String,val puntuacion: Int)