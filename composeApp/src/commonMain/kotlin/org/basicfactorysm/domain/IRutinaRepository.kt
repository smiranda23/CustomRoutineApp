package org.basicfactorysm.domain

import org.basicfactorysm.model.Ejercicio
import org.basicfactorysm.model.Rutina
import org.basicfactorysm.model.Serie
import org.basicfactorysm.model.SerieFinalizada
import org.basicfactorysm.model.Training

interface IRutinaRepository {
    fun getRutinas(): List<Rutina>
    fun addRutina(rutina: Rutina)
    fun deleteRutina(idRutina: Int)

    //EJERCICIOS
    fun getIDsEjercicios(idRutina: Int): List<Int>
    fun getEjercicios(listaIds: List<Int>): List<Ejercicio>
    fun getAllExercises(): List<Ejercicio>
    fun createExercise(nombre: String)

    //Realmente añadiremos series, SeriesEntity
    fun addExercicesSelected(
        listaEjercicios: List<Ejercicio>,
        idRutina: Int
    )

    //Añadimos series al SET
    fun addSetToExercise(idEjercicio: Int, idRutina: Int, reps: Int, weight: Double)

    fun getSeriesByRutina(idRutina: Int): List<Serie>
    fun deleteSeriesRoutine(idRutina: Int)
    fun deleteSerie(idSerie: Int)

    //UPDATES
    fun actualizarCountExeRutina(count: Int, idRutina: Int)

    //Pasamos la lista de series modificada
    fun updateSetsRutina(serie: Serie)

    //TRAINING
    fun getTrainings(): List<Training>
    fun addTraining(t: Training)

    //SERIE FINALIZZADO
    fun getSeriesFinalizadas(idTraining: Int): List<SerieFinalizada>



}