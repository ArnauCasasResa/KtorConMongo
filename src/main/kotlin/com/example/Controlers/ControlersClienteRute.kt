package com.example.Controlers

import com.example.Model.Client
import com.example.Model.Fichero
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.client.result.UpdateResult
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
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

fun clientes(): List<Client>{
    var users = mutableListOf<Client>()

    val username = "arnaucasas7e7"
    val uri = "mongodb+srv://$username:$username@batalla.fspyxte.mongodb.net/?retryWrites=true&w=majority&appName=Batalla"
    val mongoClient = MongoClients.create(uri)

    // Connexió a una BD del clúster
    val dataBase = mongoClient.getDatabase("batalla")
    // Connexió a una collection de la BD
    val collecion: MongoCollection<Document> = dataBase.getCollection("clientes")

    //Consultas clientes
    val cursor: MongoCursor<Document> = collecion.find().iterator()
    while (cursor.hasNext()) { val document = cursor.next()
        document.let {
            // Convertir el documento BSON a JSON
            val json = it.toJson()

            // Configurar el deserializador JSON para que ignore claves desconocidas
            val jsonSerializer = Json {
                ignoreUnknownKeys = true
            }

            // Deserializar el JSON a una instancia de Fichero
            try {
                val client = jsonSerializer.decodeFromString<Client>(json)
                users.add(client)
            } catch (e: SerializationException) {
                println("Error de deserialización: ${e.message}")
            }
        }
    }
    mongoClient.close()

    return users
}

fun editarPuntuacion(name: String, password: String, puntuacion: Int): Long{

    val username = "arnaucasas7e7"
    val uri = "mongodb+srv://$username:$username@batalla.fspyxte.mongodb.net/?retryWrites=true&w=majority&appName=Batalla"
    val mongoClient = MongoClients.create(uri)

    // Connexió a una BD del clúster
    val dataBase = mongoClient.getDatabase("batalla")
    // Connexió a una collection de la BD
    val collecion: MongoCollection<Document> = dataBase.getCollection("clientes")

    // Define el filtro para encontrar el documento que quieres actualizar
    val filter = Filters.and(
        Filters.eq("name", name),
        Filters.eq("password", password)
    )

    // Define la actualización que quieres hacer (incrementar el valor de un campo)
    val update = Updates.inc("puntuacion", puntuacion) // Incrementa el campo por 10

    // Ejecuta la actualización
    val updateResult = collecion.updateOne(filter, update)
    val documentosChange = updateResult.modifiedCount

    mongoClient.close()

    return documentosChange
}

