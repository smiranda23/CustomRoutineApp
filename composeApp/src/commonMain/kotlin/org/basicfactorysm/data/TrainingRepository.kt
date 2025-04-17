package org.basicfactorysm.data

import com.basicfactory.db.DatabaseBF
import org.basicfactorysm.domain.ITrainingRepository
import org.basicfactorysm.model.SerieFinalizada
import org.basicfactorysm.model.Training

class TrainingRepository(private val database: DatabaseBF) : ITrainingRepository {

    private val queriesTraining = database.trainingDbQueries
    private val queriesSeriesFinished = database.serieFinalizadaDbQueries

    override fun getTrainings(): List<Training> {
        return queriesTraining.selectAll().executeAsList().map {
            Training(
                id = it.id.toInt(),
                name = it.name,
                duration = it.duration.toInt(),
                date = it.date,
                setsFinished = it.setsFinished.toInt()
            )
        }
    }

    override fun getExercisesPerformed() {
        TODO("Not yet implemented")
    }

    override fun getSeriesFinished(idTraining: Int):List<SerieFinalizada> {
        return queriesSeriesFinished.selectFromIdTraining(idTraining.toLong()).executeAsList().map{
            SerieFinalizada(
                id = it.id.toInt(),
                idTraining = it.idTraining.toInt(),
                idEjercicio = it.idEjercicio.toInt(),
                reps = it.reps.toInt(),
                weight = it.weight
            )
        }
    }
}