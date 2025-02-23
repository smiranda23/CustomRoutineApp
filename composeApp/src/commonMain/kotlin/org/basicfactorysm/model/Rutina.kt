package org.basicfactorysm.model

import kotlinx.serialization.Serializable

@Serializable
data class Rutina(
    var id: Int,
    var nombre: String,
    var dayWeek: Int, //1=Lunes...7=Domingo
    var cantidadEjercicios: Int,
    var duracion:Int
) {
}