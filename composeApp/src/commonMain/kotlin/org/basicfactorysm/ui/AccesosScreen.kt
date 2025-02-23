package org.basicfactorysm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.presentacion.AccesViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.utils.backgroundApp

@Composable
fun AccesosScreen(nav: Navigator, accesViewModel: AccesViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(backgroundApp)) {
        TopBarBasicFactory("Accesos", nav)
        ListaAccesos(accesViewModel)
    }
}

@Composable
fun ListaAccesos(accesViewModel: AccesViewModel) {

}
