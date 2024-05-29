package com.example.Controlers

import com.example.Model.Client
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.Document


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

fun pedirClientes(): List<String>{
    var users = mutableListOf<String>()

    val username = "arnaucasas7e7"
    val uri = "mongodb+srv://$username:$username@batalla.fspyxte.mongodb.net/?retryWrites=true&w=majority&appName=Batalla"
    val mongoClient = MongoClients.create(uri)

    // Connexió a una BD del clúster
    val dataBase = mongoClient.getDatabase("batalla")
    // Connexió a una collection de la BD
    val collecion: MongoCollection<Document> = dataBase.getCollection("clientes")

    //Consultas clientes
    val cursor: MongoCursor<Document> = collecion.find().iterator()
    while (cursor.hasNext()) {
        val json = cursor.next().toJson()
        val cliente = Json.decodeFromString<Client>(json)
        users.add(cliente.name)
    }

    return users
}
