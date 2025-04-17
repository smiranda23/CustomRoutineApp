package org.basicfactorysm.ui.rutinas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Train
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Training
import org.basicfactorysm.navigation.Rutas
import org.basicfactorysm.presentacion.RutinasViewModel
import org.basicfactorysm.presentacion.TrainingViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.utils.backgroundApp

@Composable
fun EntrenamientosScreen(nav: Navigator, trainingViewModel: TrainingViewModel, rutinasViewModel: RutinasViewModel){
    Box(modifier = Modifier.fillMaxSize().background(backgroundApp)) {

        Column(modifier = Modifier.fillMaxSize()) {
            TopBarBasicFactory("Rutinas", nav)
            ListaEntrenamientos(trainingViewModel, nav)
        }

        Box(contentAlignment = Alignment.BottomCenter){
            BottomBarNav(rutinasViewModel, trainingViewModel)
        }
    }
}

@Composable
fun ListaEntrenamientos(trainingViewModel: TrainingViewModel, nav: Navigator) {

    val listaTraining = trainingViewModel.listTrainings
    LazyColumn {
        items(listaTraining) {
            ItemTraining(it,trainingViewModel,nav)
        }
    }
}

@Composable
fun ItemTraining(training: Training, trainingViewModel: TrainingViewModel, nav: Navigator) {
    Card(
        modifier = Modifier.fillMaxWidth().height(120.dp).padding(10.dp)
            .clickable {
                trainingViewModel.onClickTraining(training.id)
                nav.navigate(Rutas.DetalleTraining.ruta)
            }, elevation = 10.dp
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    Icons.Default.Checklist,
                    contentDescription = "iconChecklist",
                    tint = Color.White,
                    modifier = Modifier.background(
                        Color(0xFFffc200),
                        shape = RoundedCornerShape(20.dp)
                    ).padding(5.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(training.name, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(training.setsFinished.toString() + " series realizadas")
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "iconArrow",
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }
    }
}