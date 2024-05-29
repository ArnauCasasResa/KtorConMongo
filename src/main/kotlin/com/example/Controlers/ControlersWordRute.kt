package com.example.Controlers

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import kotlinx.coroutines.runBlocking
import org.bson.Document


fun connectionFiles(file: String) {
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

        val collection = database.getCollection("ficheros")
        collection.drop()

        runBlocking {
            database.runCommand(Document("ping", 1))

        }
        println("Pinged your deployment. You successfully connected to MongoDB!")

        subirFichero(collection, file)

    }
}


fun subirFichero(collection: MongoCollection<Document>, file: String) {
    val document = Document("file", file)
    collection.insertOne(document)
    println("Inserted file into the 'palabras' collection.")
}