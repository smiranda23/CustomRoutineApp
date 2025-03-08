package org.basicfactorysm.ui.rutinas

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.press_banca
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Ejercicio
import org.basicfactorysm.navigation.Rutas
import org.basicfactorysm.presentacion.RutinasViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun AddExerciseScreen(rutinasViewModel: RutinasViewModel, navigator: Navigator) {

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        TopBarCancelCreateExercise(navigator, rutinasViewModel)
        ContentScreen(rutinasViewModel, navigator)
    }
}


@Composable
fun TopBarCancelCreateExercise(navigator: Navigator, rutinasViewModel: RutinasViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { navigator.goBack() }) {
            Text("Cancelar")
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = {
            navigator.navigate(Rutas.CreateExercise.ruta)
            rutinasViewModel.nombreExercise = ""
        }) {
            Text("Crear")
        }

    }
}

@Composable
fun ContentScreen(rutinasViewModel: RutinasViewModel, navigator: Navigator) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val listaAllExercises = rutinasViewModel.listaAllExercises
        val count = listaAllExercises.count { it.selected.value }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ListaEjerciciosDisponibles(listaAllExercises)
        }

        if (count > 0) {
            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {
                    rutinasViewModel.addExercicesSelectedToListUI()
                    navigator.goBack()
                }) {
                Text("Añadir $count ejercicios")
            }
        }
    }
}


@Composable
fun ListaEjerciciosDisponibles(listaAllExercises: List<Ejercicio>) {

    LazyColumn {
        items(listaAllExercises) {
            ItemEjercicioForAdd(it)
        }
    }
}

@Composable
fun ItemEjercicioForAdd(ejercicio: Ejercicio) {
    Card(modifier = Modifier.fillMaxWidth().height(80.dp).clickable {
        ejercicio.selected.value = !ejercicio.selected.value
    }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val imgExercise = painterResource(Res.drawable.press_banca)
            Image(
                painter = imgExercise,
                contentDescription = "imageExercise",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(0.9f)) {
                Text(ejercicio.nombre, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Box(modifier = Modifier.weight(0.1f)) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "checkIcon",
                    tint = if (ejercicio.selected.value) Color.Green else Color.Gray
                )
            }
        }
    }
}
