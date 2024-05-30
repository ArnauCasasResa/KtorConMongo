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
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
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

fun fichero(): String {
    var files = mutableListOf<String>()

    val username = "arnaucasas7e7"
    val uri = "mongodb+srv://$username:$username@batalla.fspyxte.mongodb.net/?retryWrites=true&w=majority&appName=Batalla"
    val mongoClient = MongoClients.create(uri)

    // Connexió a una BD del clúster
    val dataBase = mongoClient.getDatabase("batalla")
    // Connexió a una collection de la BD
    val collecion: MongoCollection<Document> = dataBase.getCollection("ficheros")

    //Consultas clientes
    val cursor: MongoCursor<Document> = collecion.find().iterator()
    while (cursor.hasNext()) {
        val document = cursor.next()
        document.let {
            // Convertir el documento BSON a JSON
            val json = it.toJson()

            // Configurar el deserializador JSON para que ignore claves desconocidas
            val jsonSerializer = Json {
                ignoreUnknownKeys = true
            }

            // Deserializar el JSON a una instancia de Fichero
            try {
                val fichero = jsonSerializer.decodeFromString<Fichero>(json)
                files.add(fichero.file)
            } catch (e: SerializationException) {
                println("Error de deserialización: ${e.message}")
            }
        }

    }
    mongoClient.close()

    return files[files.lastIndex]
}


fun pedirPalabras(): Map<String, List<String>> {
    val palabras = fichero()

    // Agrupar las palabras por sus últimas dos letras
    val gruposDeRima = palabras.split(" ").groupBy {palabra ->
         if (palabra.length >= 2) palabra.takeLast(2) else palabra
    }

    // Imprimir los grupos de palabras que riman
    /*
    for ((rima, grupo) in gruposDeRima) {
        println("Rima \"$rima\": ${grupo.joinToString(", ")}")
    }
     */

    return gruposDeRima
}

fun grupoRima(): List<String> {
    var rimas : List<String>
    val cantidadPalabras = 5

    val gruposRimas = pedirPalabras().values
    do{
        rimas = gruposRimas.random()
    } while (rimas.size < cantidadPalabras)

    rimas = rimas.shuffled().take(cantidadPalabras)

    return rimas
}
