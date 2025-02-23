package org.basicfactorysm.model

import kotlinx.serialization.Serializable

@Serializable
data class Serie(
    var id: Int,
    var idEjercicio: Int,
    var idRutina: Int,
    var reps: Int,
    var weight: Double
){}