package org.basicfactorysm.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.basicfactorysm.model.Clase

interface ClaseRepInterface {

    //Funcion que sirve para SUMAR +1 en la variabla de 'apuntados' en la clase con el ID que recibe por parámetro
    suspend fun apuntarseClase(idClase: Int)

    //Funcion que sirve para RESTAR -1 en la variabla de 'apuntados' en la clase con el ID que recibe por parámetro
    suspend fun desApuntarseClase(idClase: Int)

    suspend fun getClasesPorDia(today: LocalDate): List<Clase>

    suspend fun Ping(): String

}
