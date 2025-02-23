package org.basicfactorysm.presentacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.basicfactorysm.domain.IGlobalRepository
import org.basicfactorysm.model.Entrenador

class EntrenadoresViewModel(private val repository: IGlobalRepository) : ViewModel() {

    private var _listaEntrenadores by mutableStateOf(listOf<Entrenador>())
    val listaEntrenadores: List<Entrenador> get() = _listaEntrenadores

    init {
        getEntrenadores()
    }

    fun getEntrenadores() {
        viewModelScope.launch {
            _listaEntrenadores = repository.getEntrenadores()
        }

    }


}