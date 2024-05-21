package com.example.plugins

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import org.bson.Document

fun Application.configureRouting() {
    routing {
        post("/post") {
            connection()
        }
    }
}

fun connection() {
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
        val collection = database.getCollection("palabras")

        runBlocking {
            database.runCommand(Document("ping", 1))
        }
        println("Pinged your deployment. You successfully connected to MongoDB!")


        insertarPalabras(collection)

    }
}
fun insertarPalabras(collection: MongoCollection<Document>) {


    val json = Json { ignoreUnknownKeys = true }

    val palabra= Palabra("caracola","ola")

    runBlocking {
        val listaPalabras = mutableListOf<Document>()
        val jsonText = json.encodeToString(palabra)
        val document = Document.parse(jsonText)
        listaPalabras.add(document)
        collection.insertOne(document)
    }

    println("Inserted two students into the 'grades' collection.")
}

@Serializable
class Palabra(val palabra: String, val rima: String)