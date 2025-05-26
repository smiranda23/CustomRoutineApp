package org.basicfactorysm.data

import com.basicfactory.db.DatabaseBF
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.LocalDate
import org.basicfactorysm.API_URL
import org.basicfactorysm.domain.IGlobalRepository
import org.basicfactorysm.model.Clase
import org.basicfactorysm.model.Entrenador
import org.basicfactorysm.model.Receta
import org.basicfactorysm.model.Rutina

class GlobalRepository(private val httpClient: HttpClient, private val database: DatabaseBF) : IGlobalRepository {

    private val queries = database.rutinasDbQueries


    override suspend fun getRecetas(): List<Receta> {
        TODO("Not yet implemented")
    }

    override suspend fun getEntrenadores(): List<Entrenador> {
        //val response = httpClient.get("$API_URL/entrenadores")
        val response = DataManager.ListaEntrenadores
        return response
    }

    override suspend fun getClasesPorDia(today: LocalDate): List<Clase> {
        /*val networkResponse = httpClient.get("$API_URL/clases/${today}")
        val response = networkResponse.body<List<Clase>>()*/
        val response = DataManager.ListaClases

        return response
    }

    override fun getRecetasByCategoria(categoria: String): List<Receta> {
        val response = DataManager.ListaRecetas.filter { it.categoria == categoria }
        return response
    }


}