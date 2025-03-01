package org.basicfactorysm.data

import androidx.compose.runtime.MutableState
import com.basicfactory.db.DatabaseBF
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import org.basicfactorysm.domain.IRutinaRepository
import org.basicfactorysm.model.Ejercicio
import org.basicfactorysm.model.Rutina
import org.basicfactorysm.model.Serie
import org.basicfactorysm.model.SerieFinalizada
import org.basicfactorysm.model.Training

class RutinaRepository(private val database: DatabaseBF) : IRutinaRepository {

    private val queriesRutina = database.rutinasDbQueries
    private val queriesEjercicio = database.ejerciciosDbQueries
    private val queriesSeries = database.seriesDbQueries

    private val queriesTrainings = database.trainingDbQueries
    private val queriesSeriesFinalizada = database.serieFinalizadaDbQueries

    override fun getRutinas(): List<Rutina> {
        return queriesRutina.selectAll().executeAsList().map {
            Rutina(
                id = it.id.toInt(),
                nombre = it.nombre,
                dayWeek = it.dayWeek.toInt(),
                cantidadEjercicios = it.cantidadEjercicios.toInt(),
                duracion = it.duracion.toInt()
            )
        }
    }

    override fun addRutina(rutina: Rutina) {
        queriesRutina.transaction {
            queriesRutina.insert(
                nombre = rutina.nombre,
                dayWeek = rutina.dayWeek.toLong(),
                cantidadEjercicios = rutina.cantidadEjercicios.toLong(),
                duracion = rutina.duracion.toLong()
            )
        }
    }

    override fun deleteRutina(idRutina: Int) {
        queriesRutina.transaction {
            queriesRutina.delete(idRutina.toLong())
        }
    }

    //Buscamos en tabla Series, los ids de los ejercicios que contiene esa rutina
    // Obtenemos una lista<Int>, para posteriormente buscar esos ejercicios en la tabla Ejercicios!
    override fun getIDsEjercicios(idRutina: Int): List<Int> {

        val listaIdsEjercicios =
            queriesSeries.selectIdEjeFromRutina(idRutina.toLong()).executeAsList().map {
                it.toInt()
            }

        return listaIdsEjercicios
    }

    override fun getEjercicios(listaIds: List<Int>): List<Ejercicio> {
        return listaIds.mapNotNull { id ->
            queriesEjercicio.selectEjeForId(id.toLong()).executeAsOneOrNull()?.let {
                Ejercicio(
                    id = it.id.toInt(),
                    nombre = it.nombre,
                    muscleGroup = it.muscleGroup.toString(),
                    equipment = it.equipment.toString(),
                    unilateral = it.unilateral.toInt() == 1, //Si es 1, será TRUE, si no FALSE
                    imagen = it.imagen,
                    tipoRegistro = it.tipoRegistro.toString()
                )
            }
        }
    }

    override fun getAllExercises(): List<Ejercicio> {
        return queriesEjercicio.selectAll().executeAsList().map {
            Ejercicio(
                id = it.id.toInt(),
                nombre = it.nombre,
                muscleGroup = it.muscleGroup.toString(),
                equipment = it.equipment.toString(),
                unilateral = it.unilateral.toInt() == 1, //Si es 1, será TRUE, si no FALSE
                imagen = it.imagen,
                tipoRegistro = it.tipoRegistro.toString()
            )
        }
    }

    override fun createExercise(nombre: String) {
        queriesEjercicio.transaction {
            queriesEjercicio.insert(nombre, 0, 0, 0, "img", 1)
        }
    }

    //Realmente añadiremos series, SeriesEntity, solo añadiremos 1 set por cada ejercicio,
    //set que estara con 0 reps y 0 peso, el usuario posteriormente lo modificará
    override fun addExercicesSelected(listaEjercicios: List<Ejercicio>, idRutina: Int) {
        val listaIds = listaEjercicios.map { it.id }

        listaIds.map { idEjercicio ->
            queriesSeries.transaction {
                queriesSeries.insert(idEjercicio.toLong(), idRutina.toLong(), 0, 0.0)
            }
        }
    }

    override fun addSetToExercise(idEjercicio: Int, idRutina: Int, reps: Int, weight: Double) {
        queriesSeries.transaction {
            queriesSeries.insert(idEjercicio.toLong(), idRutina.toLong(), reps.toLong(), weight)
        }
    }

    override fun actualizarCountExeRutina(count: Int, idRutina: Int) {
        //Actualizamos la cantidad de ejercicios de la rutina
        queriesRutina.transaction {
            queriesRutina.updateCantidadEjercicios(count.toLong(), idRutina.toLong())
        }
    }

    override fun updateSetsRutina(serie: Serie) {
        queriesSeries.transaction {
            queriesSeries.updateWeightReps(
                serie.reps.toLong(),
                serie.weight,
                serie.id.toLong()
            )
        }
    }

    override fun getSeriesByRutina(idRutina: Int): List<Serie> {
        return queriesSeries.selectSeriesFromEjeAndRutina(idRutina.toLong()).executeAsList().map {
            Serie(
                id = it.id.toInt(),
                idEjercicio = it.idEjercicio.toInt(),
                idRutina = it.idRutina.toInt(),
                reps = it.reps.toInt(),
                weight = it.weight.toDouble()
            )
        }
    }

    override fun deleteSeriesRoutine(idRutina: Int) {
        queriesSeries.transaction {
            queriesSeries.deleteAllSeriesByRoutine(idRutina.toLong())
        }
    }

    override fun deleteSerie(idSerie: Int) {
        queriesSeries.transaction {
            queriesSeries.delete(idSerie.toLong())
        }
    }

    override fun getTrainings(): List<Training> {

        return queriesTrainings.selectAll().executeAsList().map {
            Training(
                id = it.id.toInt(),
                name = it.name,
                dateTime = LocalDateTime.parse(it.datetime.toString()),
                setsFinished = it.setsFinished.toInt()
            )
        }

    }

    override fun addTraining(t: Training) {
        queriesTrainings.transaction {
            queriesTrainings.insert(
                name = t.name,
                datetime = 1,
                setsFinished = t.setsFinished.toLong()
            )
        }
    }

    override fun getSeriesFinalizadas(idTraining: Int): List<SerieFinalizada> {

        return queriesSeriesFinalizada.selectFromIdTraining(idTraining.toLong()).executeAsList()
            .map {
                SerieFinalizada(
                    id = it.id.toInt(),
                    idTraining = it.idTraining.toInt(),
                    idEjercicio = it.idEjercicio.toInt(),
                    reps = it.reps.toInt(),
                    weight = it.weight.toDouble()
                )
            }
    }


}
















