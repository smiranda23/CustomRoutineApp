package org.basicfactorysm.model

import kotlinx.serialization.Serializable


@Serializable
data class Ingrediente(
    var id: Int,
    var nombre: String,
    var cantidad: Int,
    var medida: String,
    var img: String
)
