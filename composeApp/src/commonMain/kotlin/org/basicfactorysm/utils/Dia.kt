package org.basicfactorysm.utils

import kotlinx.datetime.LocalDate

data class Dia(
    var clicado: Boolean = false,
    val fecha: LocalDate,
    var d: Int,
    var diaSemana: String = "",
)