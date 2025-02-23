package org.basicfactorysm.presentacion

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.basicfactorysm.domain.IRutinaRepository
import org.basicfactorysm.model.Ejercicio
import org.basicfactorysm.model.Rutina
import org.basicfactorysm.model.Serie

class RutinasViewModel(private val repository: IRutinaRepository) : ViewModel() {


    private var _listaRutinas by mutableStateOf(listOf<Rutina>())
    val listaRutinas: List<Rutina> get() = _listaRutinas

    var tipoRutinaSeleccionada: String = ""

    var rutinaSeleccionada by mutableStateOf<Rutina?>(null)

    private var _listaEjerciciosByRutina by mutableStateOf(listOf<Ejercicio>())
    val listaEjerciciosByRutina: List<Ejercicio> get() = _listaEjerciciosByRutina

    var listaIdsEjerciciosByRutina: List<Int> = emptyList()

    //Almacenamos TODAS las series de esa RUTINA!
    private var _listaSeries by mutableStateOf(listOf<Serie>())
    val listaSeries: List<Serie> get() = _listaSeries

    //Lista que usaremos en pantalla Editar Rutina, para almacenar los nuevos sets
    //que ponga, pero que NO insertaremos en BBDD hasta que le de a guardar cambios.
    var listaSeriesAux by mutableStateOf(listOf<Serie>())


    //LISTA DE TODOS LOS EJERCICIOS DE LA TABLA, LO MOSTRAMOS CUANDO EL USUARIO QUIERE AÑADIR UN EJERCICIO
    private var _listaAllExercises by mutableStateOf(listOf<Ejercicio>())
    val listaAllExercises: List<Ejercicio> get() = _listaAllExercises


    init {
        getRutinas()
        getAllExercises()
    }

    private fun getAllExercises() {
        viewModelScope.launch {
            _listaAllExercises = repository.getAllExercises()
        }
    }

    private fun getRutinas() {
        viewModelScope.launch {
            _listaRutinas = repository.getRutinas()
        }
        //animacionDelete.value = true

    }

    //Variable que se completa cuando haces clic en una rutina, y es la que usamos
    //para las siguientes pantallas
    var idRutinaSeleccionada = 0

    fun onClickRutina(idRutina: Int) {
        rutinaSeleccionada = _listaRutinas.find { it.id == idRutina }
        idRutinaSeleccionada = idRutina

        buscarSeriesyEjerciciosByRutina(idRutina)
    }

    private fun buscarSeriesyEjerciciosByRutina(idRutina: Int) {
        getAllExercises()
        listaIdsEjerciciosByRutina = repository.getIDsEjercicios(idRutina)

        _listaEjerciciosByRutina = repository.getEjercicios(listaIdsEjerciciosByRutina)

        //Buscamos todas las series de esa rutina, posteriormente filtraremos
        // por el idEjercicio de cada objeto Serie para mostrarlo en ItemEjercicio
        _listaSeries = repository.getSeriesByRutina(idRutina)

        listaSeriesAux = _listaSeries

        //Al añadir ejercicio nuevo, solo mostraremos la lista de los que NO estan en la listaEjerciciosByRutina
        val idsEjerciciosByRutina = _listaEjerciciosByRutina.map { it.id }.toSet()

        /*_listaAllExercises = _listaAllExercises.filter { exe ->
            exe.id !in idsEjerciciosByRutina
        }*/

    }


    //-----SECTION CRUD RUTINA-----//

    //MOSTRAR DIALOG ADD RUTINA
    private val _showCreateRutina = mutableStateOf(false)
    val showCreateRutina: State<Boolean> get() = _showCreateRutina

    //MOSTRAR DIALOG CONFIRMACION DELETE RUTINA
    private val _showConfirmDeleteRutina = mutableStateOf(false)
    val showConfirmDeleteRutina: State<Boolean> get() = _showConfirmDeleteRutina

    var nombre by mutableStateOf("")


    fun showDialogCreateRutina() {
        _showCreateRutina.value = true
    }

    fun hideDialogCreateRutina() {
        _showCreateRutina.value = false
    }

    fun createRutina() {
        val rutina = Rutina(0, nombre, 2, 0, 0)
        viewModelScope.launch {
            repository.addRutina(rutina)
            getRutinas()
        }
        nombre = ""
        hideDialogCreateRutina()
    }

    //Funcion que llamamos cuando swipeamos
    fun confirmarDeleteRutina(idRutina: Int) {
        rutinaSeleccionada = _listaRutinas.find { it.id == idRutina }
        _showConfirmDeleteRutina.value = true
    }

    fun hideConfirmDeleteRutina() {
        _showConfirmDeleteRutina.value = false
    }

    fun deleteRutina(idRutina: Int) {
        viewModelScope.launch {
            repository.deleteRutina(idRutina)
            getRutinas()
        }
        hideConfirmDeleteRutina()
    }

    //------SECTION EDIT RUTINA ----//

    //-----SECTION CRUD EJERCICIO-----//
    var nombreExercise by mutableStateOf("")


    //Lo que haremos realmente es añadir Series a la lista _listaEjerciciosByRutina y _listaSeries,
    //para posteriormente si guarda cambios, se insertaran en la BBDD
    fun addExercicesSelectedToListUI() {
        val listaExercicesSelected = _listaAllExercises.filter { it.selected.value }


        listaExercicesSelected.forEach {
            _listaEjerciciosByRutina = _listaEjerciciosByRutina + it
            addSetForListaUI(it.id, idRutinaSeleccionada)
        }


        //buscarSeriesyEjerciciosByRutina(idRutinaSeleccionada)
        //getRutinas()
        //rutinaSeleccionada = _listaRutinas.find { it.id == idRutinaSeleccionada }
    }

    private fun addExerciseSelected() {
        //---ESTO LO HACMEOS CUANDO INSERTAMOS EN BBDD---//
        //Update count exercises from RutinaEntity
        /*val count = listaExercicesSelected.size + rutinaSeleccionada!!.cantidadEjercicios
        viewModelScope.launch {
            repository.addExercicesSelected(listaExercicesSelected, idRutinaSeleccionada)
            repository.actualizarCountExeRutina(count, idRutinaSeleccionada)
        }*/
    }

    //DELETE EXERCISE, PENDIENTE DE IMPLEMENTAR, DAREMOS OPCION DED BORRAR EJERCICIO COMPLETO
    //ACTUALMENTE LO QUE PUEDES HACER ES BORRAR TODAS LAS SERIES DE ESE EJERCICIO PARA ELIMINAR
    //ESE EJERCICIO

    private val _showConfirmDeleteExercise = mutableStateOf(false)
    val showConfirmDeleteExercise: State<Boolean> get() = _showConfirmDeleteExercise

    //Funcion que llamamos cuando swipeamos
    var idEjercicioSelected: Int = 0
    fun confirmarDeleteExercise(idEjercicio: Int) {
        _showConfirmDeleteExercise.value = true
        idEjercicioSelected = idEjercicio
    }

    fun hideConfirmDeleteExercise() {
        _showConfirmDeleteExercise.value = false
    }

    //Borramos de la bbdd las SeriesEntity, y el ejercicios lo eliminamos
    //de la lista _listaEjerciciosByRutina, NO de EjerciciosEntity
    //------NO SE USA----//
    fun deleteExerciseAndSeries() {
        _listaEjerciciosByRutina = _listaEjerciciosByRutina.filter { it.id != idEjercicioSelected }

        viewModelScope.launch {
            repository.deleteSeriesByEjercicio(idEjercicioSelected, rutinaSeleccionada!!.id)
        }
        hideConfirmDeleteExercise()
        buscarSeriesyEjerciciosByRutina(rutinaSeleccionada!!.id)

        val count = _listaEjerciciosByRutina.size
        viewModelScope.launch {
            repository.actualizarCountExeRutina(count, rutinaSeleccionada!!.id)
        }

        getRutinas()
        rutinaSeleccionada = _listaRutinas.find { it.id == rutinaSeleccionada!!.id }
    }

    //------SECTION CRUD SERIE-----//

    fun deleteSerieByListaUI(serie: Serie) {
        _listaSeries = _listaSeries - serie
    }

    fun deleteSerie(idSerie: Int) {
        repository.deleteSerie(idSerie)
        buscarSeriesyEjerciciosByRutina(idRutinaSeleccionada)
    }

    private val _showCambiosPendientes = mutableStateOf(false)
    val showCambiosPendientes: State<Boolean> get() = _showCambiosPendientes

    fun showDialogSalirSinGuardar() {
        _showCambiosPendientes.value = true
    }

    fun hideDialogSalirSinGuardar() {
        _showCambiosPendientes.value = false
    }

    fun volverAtrasSinGuardar() {
        hideDialogSalirSinGuardar()
        buscarSeriesyEjerciciosByRutina(rutinaSeleccionada!!.id)
    }

    fun addSetForListaUI(idEjercicio: Int, idRutina: Int) {
        val index = _listaSeries.maxOfOrNull { it.id } ?: 0

        _listaSeries = _listaSeries + Serie(index + 1, idEjercicio, idRutina, 0, 0.0)

        //buscarSeriesyEjerciciosByRutina(idRutina)
    }

    private fun addSet(idEjercicio: Int, idRutina: Int, reps: Int, weight: Double) {
        viewModelScope.launch {
            repository.addSetToExercise(idEjercicio, idRutina, reps, weight)
        }
        buscarSeriesyEjerciciosByRutina(idRutina)
    }

    fun onValueChangeSerie(idSerie: Int, nuevoPeso: String, nuevasReps: String) {

        try {
            //Solo actualizamos cuando NO sea 'blanco' ya que si no al intentar
            //convertirlo a INT peta
            if (nuevoPeso.isNotBlank() && nuevasReps.isNotBlank()) {
                _listaSeries = _listaSeries.map { serie ->
                    if (serie.id == idSerie) {
                        serie.copy(weight = nuevoPeso.toDouble(), reps = nuevasReps.toInt())
                    } else serie
                }
            }
        } catch (e: Exception) {

        }

    }

    //------SECTION UPDATE RUTINA (ACTUALIZAMOS LAS SERIES LO QUE CONLLEVA A EJERCICIOS-----//

    fun onClickGuardarRutina() {
        //Si son IGUALES es que NO ha modificado nada, no hace falta actualizar
        if (_listaSeries == listaSeriesAux) {
            //No hacemos nada, la UI volverá a la pantalla anterior
        } else {
            //Si son DIFERENTES, tenemos que
            // ELIMINAR las que ya no esten,
            // INSERTAR las nuevas y
            // UPDATEAR las que ya teníamos

            //Detectamos las que han sido borradas
            val listaSeriesEliminadas = listaSeriesAux.filter {
                it.id !in _listaSeries.map { serie ->
                    serie.id
                }
            }

            //Detectamos las series NUEVAS
            val listaSeriesNuevas = _listaSeries.filter { it !in listaSeriesAux }

            //Si el ID de listaseriesnuevas NO EXIISTE en listaSeriesAux, entonces se INSERTAN en BBDD
            val seriesNuevas = listaSeriesNuevas.filter { serie ->
                !listaSeriesAux.any { it.id == serie.id }
            }

            val seriesModificadas = listaSeriesNuevas.filter { serie ->
                listaSeriesAux.any { it.id == serie.id }
            }

            //Por cada serie ELIMINADA, llamamos a la funcion deleteSerie
            listaSeriesEliminadas.forEach { serie ->
                deleteSerie(serie.id)
            }

            //Por cada serie NUEVA, llamamos a la funcion addSet
            seriesNuevas.forEach { serie ->
                addSet(serie.idEjercicio, serie.idRutina, serie.reps, serie.weight)
            }

            //Por cada serie MODIFICADA, llamamos a la funcion updateSet
            seriesModificadas.forEach { serie ->
                repository.updateSetsRutina(serie)
            }

        }

        buscarSeriesyEjerciciosByRutina(idRutinaSeleccionada)
    }

    //Creamos ejercicio nuevo
    fun onClickGuardarEjercicio() {
        repository.createExercise(nombreExercise)
        getAllExercises()

        //Al añadir ejercicio nuevo, solo mostraremos la lista de los que NO estan en la listaEjerciciosByRutina
        val idsEjerciciosByRutina = _listaEjerciciosByRutina.map { it.id }.toSet()

        _listaAllExercises = _listaAllExercises.filter { exe ->
            exe.id !in idsEjerciciosByRutina
        }

    }


}