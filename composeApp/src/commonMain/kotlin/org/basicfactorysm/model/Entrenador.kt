package org.basicfactorysm.model

import kotlinx.serialization.Serializable

@Serializable
data class Entrenador(
    var id: Int,
    var nombre: String,
    var correo: String,
    var telefono: String
)