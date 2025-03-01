package org.basicfactorysm.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable

@Serializable
data class Serie(
    var id: Int,
    var idEjercicio: Int,
    var idRutina: Int,
    var reps: Int,
    var weight: Double,
    var isChecked: MutableState<Boolean> = mutableStateOf(false)
) {}