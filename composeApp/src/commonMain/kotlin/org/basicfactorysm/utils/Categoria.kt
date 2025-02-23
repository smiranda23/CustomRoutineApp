package org.basicfactorysm.utils

import kotlinx.serialization.Serializable

@Serializable
data class Categoria(
    var id: Int,
    var nombre: String,
    var clicado: Boolean = false
)
