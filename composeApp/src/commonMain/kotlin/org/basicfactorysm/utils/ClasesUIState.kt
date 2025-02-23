package org.basicfactorysm.utils

import org.basicfactorysm.model.Clase

sealed class ClasesUIState {
    object Loading : ClasesUIState()
    data class Success(val listaClases: List<Clase>) : ClasesUIState()
    data class Error(val message: String) : ClasesUIState()
}

