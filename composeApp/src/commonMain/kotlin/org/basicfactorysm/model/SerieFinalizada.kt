package org.basicfactorysm.model

import kotlinx.serialization.Serializable

@Serializable
data class SerieFinalizada(
    val id: Int,
    val idTraining: Int,
    val idEjercicio: Int,
    val reps: Int,
    val weight: Double,
) {
}