package org.basicfactorysm.presentacion

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.basicfactorysm.data.DataManager
import org.basicfactorysm.domain.ClaseRepInterface
import org.basicfactorysm.domain.IGlobalRepository
import org.basicfactorysm.model.Clase
import org.basicfactorysm.model.Rutina
import org.basicfactorysm.utils.ClasesUIState
import org.basicfactorysm.utils.Dia

class ClasesViewModel(private val repository: IGlobalRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ClasesUIState>(ClasesUIState.Loading)
    val uiState = _uiState.asStateFlow()

    //var allClases = mutableStateListOf<Clase>()
    var allClases by mutableStateOf(listOf<Clase>())

    //private val _uiState = MutableStateFlow()
    var response by mutableStateOf("Esperando...")

    private var _listDayWeek by mutableStateOf(listOf<Dia>())
    val listDayWeek: List<Dia> get() = _listDayWeek

    private val _showDialogConfirm = mutableStateOf(false)
    val showDialogConfirm: State<Boolean> get() = _showDialogConfirm

    init {
        getDaysWeek()
        getClases()
    }

    private fun getDaysWeek() {
        _listDayWeek = emptyList()

        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()) // Obtiene la fecha actual

        val days = mutableListOf<Dia>()
        val dToday =
            Dia(
                clicado = true,
                today.date,
                d = today.dayOfMonth,
                diaSemana = today.dayOfWeek.toString()
            )
        days.add(dToday)

        for (i in 1..6) {
            val diaActual = today.date
            val nextDay = diaActual.plus(i, DateTimeUnit.DAY)
            val d = Dia(
                false,
                nextDay,
                d = nextDay.dayOfMonth,
                diaSemana = nextDay.dayOfWeek.toString()
            )
            days.add(d)
        }
        _listDayWeek = days
    }

    fun onClickDayWeek(fecha: LocalDate) {
        _listDayWeek = _listDayWeek.map { dia ->
            dia.copy(clicado = dia.d == fecha.dayOfMonth)
        }

        viewModelScope.launch {
            try {
                allClases = repository.getClasesPorDia(fecha)
                _uiState.value = ClasesUIState.Success(allClases)

            } catch (e: Exception) {
                _uiState.value = ClasesUIState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    private fun getClases() {
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()) // Obtiene la fecha actual
        viewModelScope.launch {
            try {
                //allClases = repository.getClasesPorDia(today.date)
                allClases = DataManager.ListaClases

                _uiState.value = ClasesUIState.Success(allClases)
            } catch (e: Exception) {
                _uiState.value = ClasesUIState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    var claseSeleccionada by mutableStateOf<Clase?>(null)

    fun onClickClase(clase: Clase) {
        claseSeleccionada = clase
        mostrarDialogConfirm()
    }

    private fun mostrarDialogConfirm() {
        _showDialogConfirm.value = true
    }

    fun cerrarDialogConfirm() {
        _showDialogConfirm.value = false
    }

    //PENDIENTE DE IMPLEMENTAR
    fun onclickReservaConfirmada() {
//        val index = DataManager.ListaClases.indexOfFirst { it.id == claseSeleccionada!!.id }
//        if (index != -1) {
//            DataManager.ListaClases[index].reservado = true
//        }
        cerrarDialogConfirm()
        //getClases()
    }

}