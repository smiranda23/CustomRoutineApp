package org.basicfactorysm.model

import kotlinx.serialization.Serializable

@Serializable
data class Clase(
    val id: Int,
    val nombre: String,
    val sala: String,
    val fecha: String,
    val horario: String,
    var apuntados: Int,
    var limite: Int,
    var monitor: String,
    var reservado: Boolean = false
)
