package org.basicfactorysm.presentacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.basicfactorysm.domain.IRutinaRepository
import org.basicfactorysm.domain.ITrainingRepository
import org.basicfactorysm.model.SerieFinalizada
import org.basicfactorysm.model.Training

class TrainingViewModel(private val repository: ITrainingRepository) : ViewModel() {



    private var _listTrainings by mutableStateOf(listOf<Training>())
    val listTrainings: List<Training> get() = _listTrainings

    private var _listaSeriesTraining by mutableStateOf(listOf<SerieFinalizada>())
    val listaSeriesTraining: List<SerieFinalizada> get() = _listaSeriesTraining

    //Objeto que se iguala al pulsar onClickTraining
    var trainSelected by mutableStateOf<Training?>(null)

    //We load the trainings when we load the app, and when the user save a new training
    init {
        getTrainings()
    }

    fun getTrainings(){
        viewModelScope.launch {
            _listTrainings = repository.getTrainings()
        }
    }

    fun onClickTraining(idTraining: Int) {
        trainSelected = _listTrainings.find { it.id == idTraining }
        loadListSeriesFinalizadas(idTraining)
    }

    private fun loadListSeriesFinalizadas(idTraining: Int){
        _listaSeriesTraining = repository.getSeriesFinished(idTraining)
    }
}