package org.basicfactorysm.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable

@Serializable
data class Ejercicio(
    var id: Int,
    var nombre: String,
    var muscleGroup: String,
    var equipment: String,
    var unilateral: Boolean,
    var imagen: String,
    var tipoRegistro: String,
    var selected: MutableState<Boolean> = mutableStateOf(false)
    /*var series: Int,
    var repeticiones: String,
    var descanso: Int,*/

)