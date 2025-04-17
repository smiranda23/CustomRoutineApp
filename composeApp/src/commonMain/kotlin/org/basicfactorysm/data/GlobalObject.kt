package org.basicfactorysm.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.basicfactorysm.model.Ejercicio

object GlobalObject {
    public var ListAllExercises by mutableStateOf(listOf<Ejercicio>())

}