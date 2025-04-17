package org.basicfactorysm.domain

import org.basicfactorysm.model.SerieFinalizada
import org.basicfactorysm.model.Training

interface ITrainingRepository {
    fun getTrainings(): List<Training>
    fun getExercisesPerformed()
    fun getSeriesFinished(idTraining: Int): List<SerieFinalizada>

}