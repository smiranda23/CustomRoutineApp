package org.basicfactorysm.domain

import kotlinx.datetime.LocalDate
import org.basicfactorysm.model.Clase
import org.basicfactorysm.model.Entrenador
import org.basicfactorysm.model.Receta
import org.basicfactorysm.model.Rutina

interface IGlobalRepository {

    suspend fun getRecetas(): List<Receta>
    suspend fun getEntrenadores(): List<Entrenador>
    suspend fun getClasesPorDia(today: LocalDate): List<Clase>
    fun getRecetasByCategoria(categoria: String): List<Receta>

}