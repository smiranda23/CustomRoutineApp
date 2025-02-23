package org.basicfactorysm.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.basicfactorysm.API_URL

import org.basicfactorysm.domain.ClaseRepInterface
import org.basicfactorysm.model.Clase



class ClasesRepository(
    private val httpClient: HttpClient
) : ClaseRepInterface {

    override suspend fun apuntarseClase(idClase: Int) {
        //Creo metodo POST apuntando a la API y le paso el id de mi clase.
        val response = httpClient.post("$API_URL/clases/${idClase}")
        val body = response.body<String>()

    }

    override suspend fun desApuntarseClase(idClase: Int) {
        //Creo metodo POST apuntando a la API y le paso el id de mi clase.
    }

    override suspend fun Ping(): String {
        //var list: List<Clase> = mutableListOf()
        //return list
        // 1- Buscar info en server
        //val networkResponse = httpClient.get("$API_URL/clases").body<List<Clase>>()
        val test = httpClient.get("$API_URL/Ping")
        val response = test.body<String>()

        return response
    }

    override suspend fun getClasesPorDia(today: LocalDate): List<Clase> {
        //var list: List<Clase> = mutableListOf()
        //return list
        // 1- Buscar info en server
        //val networkResponse = httpClient.get("$API_URL/clases/${today}")
        //val response = networkResponse.body<List<Clase>>()
        val response = DataManager.ListaClases

        return response
    }

}