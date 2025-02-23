package org.basicfactorysm.presentacion

import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.ViewModel
import org.basicfactorysm.navigation.Rutas


class HomeViewModel : ViewModel() {

    fun onClickSettings(navigator: Navigator) {
        navigator.navigate(Rutas.Settings.ruta)
    }

}