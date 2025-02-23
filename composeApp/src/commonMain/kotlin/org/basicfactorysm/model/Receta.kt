package org.basicfactorysm.model

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Resource


@Serializable
data class Receta(
    var id: Int,
    var nombre: String,
    var categoria: String,
    var descripcion: String = "",
    var calorias: Int = 0,
    var tiempo: Int = 0,
    var dificultad: String = "",
    var ingredientes: List<Ingrediente> = emptyList(),
)
