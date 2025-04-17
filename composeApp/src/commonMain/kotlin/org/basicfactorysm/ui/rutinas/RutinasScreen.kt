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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Rutina
import org.basicfactorysm.navigation.Rutas
import org.basicfactorysm.presentacion.RutinasViewModel
import org.basicfactorysm.presentacion.TrainingViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.ui.nativo.NativeTextField
import org.basicfactorysm.utils.Dia
import org.basicfactorysm.utils.SwipeToDeleteContainer
import org.basicfactorysm.utils.backgroundApp
import org.basicfactorysm.utils.colorRed

@Composable
fun RutinasScreen(nav: Navigator, rutinasViewModel: RutinasViewModel, trainingViewModel: TrainingViewModel) {

    Box(modifier = Modifier.fillMaxSize().background(backgroundApp)) {

        Column(modifier = Modifier.fillMaxSize()) {
            TopBarBasicFactory(
                screen = if(rutinasViewModel.showTrainings.value) "Entrenamientos" else "Rutinas"
                , nav)
            if(rutinasViewModel.showRoutines.value){
                ListaRutinas(rutinasViewModel, nav)
            }else if(rutinasViewModel.showTrainings.value){
                ListaEntrenamientos(trainingViewModel, nav)
            }

        }

        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.align(Alignment.BottomEnd)){
            BottomBarNav(rutinasViewModel, trainingViewModel)
        }

        if(rutinasViewModel.showRoutines.value){
            FloattingShowAddRutina(
                modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 80.dp),
                rutinasViewModel
            )
        }

        //Show dialog to add Routine
        if (rutinasViewModel.showCreateRutina.value)
            CreateRutinaDialog(rutinasViewModel)
    }
}

@Composable
fun BottomBarNav(rutinasViewModel: RutinasViewModel, trainingViewModel: TrainingViewModel) {
    Box(modifier = Modifier.background(Color.White).fillMaxWidth().padding(4.dp),
        contentAlignment = Alignment.Center) {
        Row (verticalAlignment = Alignment.CenterVertically){

            IconButton(onClick = {rutinasViewModel.onClickButtonRoutines()}) {
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Icon(Icons.Default.FitnessCenter, contentDescription = "fitnessIcon",
                        tint = if(rutinasViewModel.showRoutines.value) colorRed else Color.Black,
                        modifier = Modifier.size(40.dp))
                    Text("Rutinas", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier= Modifier.width(30.dp))

            IconButton(onClick = {rutinasViewModel.onClickButtonTrainings()
            trainingViewModel.getTrainings()}) {
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "historialIcon",
                        tint = if(rutinasViewModel.showTrainings.value)  colorRed else Color.Black,
                        modifier = Modifier.size(40.dp))
                    Text("Historial", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FloattingShowAddRutina(modifier: Modifier, rutinasViewModel: RutinasViewModel) {

    //NativeTextField("test",{},"labelTest", Modifier.width(300.dp).height(300.dp))
    IconButton(
        onClick = { rutinasViewModel.showDialogCreateRutina() },
        modifier = modifier.background(shape = RoundedCornerShape(50.dp), color = Color.White)
    ) {
        Icon(Icons.Default.Add, contentDescription = "iconAdd")
    }
}


@Composable
fun ListaRutinas(rutinasViewModel: RutinasViewModel, nav: Navigator) {

    val listaRutinas = rutinasViewModel.listaRutinas

    LazyColumn {
        items(listaRutinas, key = { it.id }) {
            SwipeToDeleteContainer(
                item = it,
                onDelete = { rutinasViewModel.confirmarDeleteRutina(it.id) }
            ) {
                ItemRutina(it, rutinasViewModel, nav)
            }
        }
    }

    if (rutinasViewModel.showConfirmDeleteRutina.value) {
        ConfirmDeleteRutinaDialog(rutinasViewModel)
    }

}

@Composable
fun ItemRutina(rutina: Rutina, rutinasViewModel: RutinasViewModel, nav: Navigator) {

    Card(
        modifier = Modifier.fillMaxWidth().height(120.dp).padding(10.dp)
            .clickable {
                rutinasViewModel.onClickRutina(rutina.id)
                nav.navigate(Rutas.DetalleRutina.ruta)
            }, elevation = 10.dp
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    Icons.Default.Bolt,
                    contentDescription = "icon",
                    tint = Color.White,
                    modifier = Modifier.background(
                        Color(0xFFffc200),
                        shape = RoundedCornerShape(20.dp)
                    ).padding(5.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(rutina.nombre, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(rutina.cantidadEjercicios.toString() + " ejercicios")
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