package org.basicfactorysm.presentacion

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.basicfactorysm.data.GlobalObject
import org.basicfactorysm.domain.IRutinaRepository
import org.basicfactorysm.model.Ejercicio
import org.basicfactorysm.model.Rutina
import org.basicfactorysm.model.Serie
import org.basicfactorysm.model.SerieFinalizada
import org.basicfactorysm.model.Training

class RutinasViewModel(private val repository: IRutinaRepository) : ViewModel() {


    //Boolens for show routines or trainings, bottom bar nav
    private val _showRoutines = mutableStateOf(true)
    val showRoutines: State<Boolean> get() = _showRoutines

    //Boolens for show routines or trainings, bottom bar nav
    private val _showTrainings = mutableStateOf(false)
    val showTrainings: State<Boolean> get() = _showTrainings



    private var _listaRutinas by mutableStateOf(listOf<Rutina>())
    val listaRutinas: List<Rutina> get() = _listaRutinas

    var rutinaSeleccionada by mutableStateOf<Rutina?>(null)

    //We use for the screen 'RutinaDetalles'
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

    //Lista de las series finalizadas de la entidad Training,
    //son las series que hizo en el entrenamiento
    private var _listaSeriesTraining by mutableStateOf(listOf<SerieFinalizada>())
    val listaSeriesTraining: List<SerieFinalizada> get() = _listaSeriesTraining


    init {
        getRutinas()
        getAllExercises()
    }

    private fun getAllExercises() {
        viewModelScope.launch {
            _listaAllExercises = repository.getAllExercises()
            GlobalObject.ListAllExercises = repository.getAllExercises()
        }
    }

    private fun getRutinas() {
        viewModelScope.launch {
            _listaRutinas = repository.getRutinas()
        }
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
        //Solo ejecutamos las llamadas cuando NO hay rutina en progreso
        if (!timerStarted.value) {
            getAllExercises()
            listaIdsEjerciciosByRutina = repository.getIDsEjercicios(idRutina)

            _listaEjerciciosByRutina = repository.getEjercicios(listaIdsEjerciciosByRutina)

            //Buscamos todas las series de esa rutina, posteriormente filtraremos
            // por el idEjercicio de cada objeto Serie para mostrarlo en ItemEjercicio
            _listaSeries = repository.getSeriesByRutina(idRutina)

            listaSeriesAux = _listaSeries
        }
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

    //When we deleted a routine, we delete all series related to that routine
    fun deleteRutina(idRutina: Int) {
        viewModelScope.launch {
            repository.deleteRutina(idRutina)
            repository.deleteSeriesRoutine(idRutina)
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
            addSetForListaUI(it.id, idRutinaSeleccionada)
        }

        //Reset parameter 'selected'
        _listaAllExercises.forEach { it.selected.value = false }

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

    //------SECTION CRUD SERIE-----//

    fun deleteSerieByListaUI(serie: Serie) {
        _listaSeries = _listaSeries - serie
    }

    private fun deleteSerie(idSerie: Int) {
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

        //Actualizamos cantidad ejercicios de rutina en SQL
        val countExercises = _listaSeries.groupBy { it.idEjercicio }.count()
        rutinaSeleccionada!!.cantidadEjercicios = countExercises
        repository.actualizarCountExeRutina(countExercises, idRutinaSeleccionada)
        getRutinas()

    }

    //Creamos ejercicio nuevo
    fun onClickGuardarEjercicio() {
        repository.createExercise(nombreExercise)
        getAllExercises()
    }

    //-----START ROUTINE SCREEN-------//

    //TIMER
    private val _timer = mutableStateOf(0) // Contador en segundos
    val timer: State<Int> get() = _timer

    private val _timerStarted = mutableStateOf(false)
    val timerStarted: State<Boolean> get() = _timerStarted

    fun setearCheckedMap() {
        // Sincronizamos el estado de isCheckedMap con _listaSeries
        _listaSeries.forEach { serie ->
            isCheckedMap.put(serie.id, serie.isChecked.value) // Si ya existe, no lo sobrescribe
        }
    }

    fun iniciarTimer() {
        if (!_timerStarted.value) {
            _timerStarted.value = true
            startTimer()
        } else {
            //Si ya esta iniciado, no hacemos nada
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_timerStarted.value) {
                delay(1000L)
                _timer.value += 1
            }
        }
    }

    fun detenerTimer() {
        _timerStarted.value = false
    }

    fun reiniciarTimer() {
        _timerStarted.value = false
        _timer.value = 0
    }

    fun getFormattedTime(): String {
        val minutos = _timer.value / 60
        val segundos = _timer.value % 60
        if (minutos == 0) {
            return "$segundos";
        } else {
            return "$minutos min:$segundos"
        }

        //return String.format("%02d:%02d", minutos, segundos)
    }

    //CLICK FINALIZAR RUTINA
    fun onClickFinalizarRutina() {
        //Al finalizar rutina, comprobamos si hay alguna serie con el check de 'hecho'
        val listaSeriesFinalizadas = listaSeries.filter { it.isChecked.value == true }

        //Si hay series con el 'check'
        if (listaSeriesFinalizadas.isNotEmpty()) {
            showDialogConfirmEndRoutine()
        } else {
            //Si no hay ninguna serie con el check, lanzamos OTRO mensaje
            showDialogInfoRutinaSinValores()
        }

        hideDialogCreateRutina()
    }

    // POP UP CONFIRMAR FINALIZAR RUTINA
    private val _showConfirmEndRoutine = mutableStateOf(false)
    val showConfirmEndRoutine: State<Boolean> get() = _showConfirmEndRoutine

    private fun showDialogConfirmEndRoutine() {
        _showConfirmEndRoutine.value = true
    }

    fun hideDialogConfirmEndRoutine() {
        _showConfirmEndRoutine.value = false
    }

    //Usamos para mostrar dialog, que informa cuando intentas
    //finalizar la rutina y no has 'checkeado' ninguna serie
    private val _showInfoRutinaSinValores = mutableStateOf(false)
    val showInfoRutinaSinValores: State<Boolean> get() = _showInfoRutinaSinValores

    private fun showDialogInfoRutinaSinValores() {
        _showInfoRutinaSinValores.value = true
    }

    fun hideDialogInfoRutinaSinValores() {
        _showInfoRutinaSinValores.value = false
    }

    fun saveTraining() {

        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        //Contamos cuantas series ha marcado como finalizada
        val count = isCheckedMap.count { it.value }
        rutinaSeleccionada?.let {
            val training = Training(
                id = 0,
                name = it.nombre,
                duration = timer.value,
                date = currentDate.toString(),
                setsFinished = count
            )

            viewModelScope.launch {
                repository.addTraining(training)
            }
        }

        //Despues de crear el training, ya podemos guardar las series finalizadas
        //ya que tenemos el ID del training

        val idTrainig = repository.getTrainings().last().id
        saveSeriesFinalizadas(idTrainig)

    }

    fun saveSeriesFinalizadas(idTraining: Int) {
        val idSeriesFinalizadas = isCheckedMap.filter { it.value }

        val listaSeriesFinalizadas = _listaSeries.filter { it.id in idSeriesFinalizadas.keys }

        listaSeriesFinalizadas.forEach { s ->
            repository.addSerieFinalizada(idTraining, s.idEjercicio, s.reps, s.weight)
        }
    }


    // Mapa para gestionar el estado de "isChecked"
    private val isCheckedMap = mutableStateMapOf<Int, Boolean>()

    fun modificarSerieChecked(s: Serie) {
        isCheckedMap[s.id] = !(isCheckedMap[s.id] ?: false)
    }




    fun onClickButtonRoutines() {
        _showTrainings.value = false
        _showRoutines.value = true
    }

    fun onClickButtonTrainings() {
        _showTrainings.value = true
        _showRoutines.value = false
    }

    //Usamos para mostrar dialog para confimar cancelacion de entrenamiento
    private val _showDialogCancelWorkout = mutableStateOf(false)
    val showDialogCancelWorkout: State<Boolean> get() = _showDialogCancelWorkout

    private fun showDialogCancelWorkout() {
        _showDialogCancelWorkout.value = true
    }

    fun hideDialogCancelWorkout() {
        _showDialogCancelWorkout.value = false
    }
    fun onClickDiscardWorkout() {
        showDialogCancelWorkout()
    }




}