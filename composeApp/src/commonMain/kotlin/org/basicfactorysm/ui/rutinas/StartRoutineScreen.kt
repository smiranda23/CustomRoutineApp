package org.basicfactorysm.ui.rutinas

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.press_banca
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Serie
import org.basicfactorysm.navigation.Rutas
import org.basicfactorysm.presentacion.RutinasViewModel
import org.basicfactorysm.ui.nativo.NativeTextField
import org.basicfactorysm.utils.SwipeToDeleteSet
import org.basicfactorysm.utils.colorRed
import org.jetbrains.compose.resources.painterResource
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun StartRoutineScreen(navigator: Navigator, rutinasViewModel: RutinasViewModel) {

    val listaSeries = rutinasViewModel.listaSeries

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column {
            Box(modifier = Modifier.weight(0.07f)) {
                TopBarStartRoutine(navigator, rutinasViewModel)
            }
            Box(modifier = Modifier.weight(0.1f)) {
                TimerRoutine(rutinasViewModel)
            }
            Box(modifier = Modifier.weight(0.8f)) {
                ListaSetsStartRoutine(listaSeries, rutinasViewModel, navigator)
            }
//            Box(modifier = Modifier.weight(0.1f)) {
//                Divider(modifier = Modifier.fillMaxWidth().height(4.dp).background(Color.DarkGray))
//                ButtonAddEjercicio(navigator)
//            }
        }
    }

    if (rutinasViewModel.showConfirmEndRoutine.value) {
        DialogConfirmEndRoutine(rutinasViewModel, navigator)
    }
    if (rutinasViewModel.showInfoRutinaSinValores.value) {
        DialogInfoRoutineSinValores(navigator, rutinasViewModel)
    }
    if (rutinasViewModel.showDialogCancelWorkout.value) {
        DialogCancelWorkout(rutinasViewModel, navigator)
    }

}

@Composable
fun DialogCancelWorkout(rutinasViewModel: RutinasViewModel, navigator: Navigator) {
    AlertDialog(
        onDismissRequest = { rutinasViewModel.hideDialogInfoRutinaSinValores() },
        title = { Text("Cancelar") },
        text = { Text("¿Seguro que desea descartar el entrenamiento?") },
        confirmButton = {
            TextButton(onClick = {
                navigator.goBack()
                rutinasViewModel.hideDialogCancelWorkout()
                rutinasViewModel.reiniciarTimer()
            }) {
                Text("Descartar entrenamiento", fontWeight = FontWeight.Bold, color = Color(0Xffdf3d3d))
            }
        },
        dismissButton = {
            TextButton(onClick = { rutinasViewModel.hideDialogCancelWorkout() }) {
                Text("Cancelar", fontWeight = FontWeight.Bold)
            }
        }
    )
}


@Composable
fun TopBarStartRoutine(navigator: Navigator, rutinasViewModel: RutinasViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextButton(onClick = { navigator.goBack() }) {
            Text("Cancelar", color = Color.White)
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Blue),
            shape = RoundedCornerShape(50.dp),
            onClick = {
                rutinasViewModel.onClickFinalizarRutina()
            }) {
            Text("Finalizar!", color = Color.White)
        }
    }
}

@Composable
fun TimerRoutine(rutinasViewModel: RutinasViewModel) {
    val temporizador = rutinasViewModel.getFormattedTime()

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Duración: ${temporizador}s",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun DialogConfirmEndRoutine(rutinasViewModel: RutinasViewModel, navigator: Navigator) {

    AlertDialog(
        onDismissRequest = { rutinasViewModel.hideDialogConfirmEndRoutine() },
        title = { Text("Finalizar") },
        text = { Text("¿Finalizar y guardar rutina?") },
        confirmButton = {
            TextButton(onClick = {
                rutinasViewModel.hideDialogConfirmEndRoutine()
                rutinasViewModel.reiniciarTimer()
                rutinasViewModel.saveTraining()
                rutinasViewModel.onClickGuardarRutina()
                navigator.goBack()

            }) {
                Text("Guardar", color = Color.Green, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = { rutinasViewModel.hideDialogConfirmEndRoutine() }) {
                Text("Cancelar", fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun DialogInfoRoutineSinValores(navigator: Navigator, rutinasViewModel: RutinasViewModel) {

    AlertDialog(
        onDismissRequest = { rutinasViewModel.hideDialogInfoRutinaSinValores() },
        title = { Text("Finalizar") },
        text = { Text("La rutina no tiene ninguna serie completada") },
        confirmButton = {
            TextButton(onClick = {
                navigator.goBack()
                rutinasViewModel.hideDialogInfoRutinaSinValores()
                rutinasViewModel.reiniciarTimer()
            }) {
                Text("Finalizar igualmente", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = { rutinasViewModel.hideDialogInfoRutinaSinValores() }) {
                Text("Cancelar", fontWeight = FontWeight.Bold)
            }
        }
    )
}


@Composable
fun ListaSetsStartRoutine(listaSeries: List<Serie>, rutinasViewModel: RutinasViewModel, nav: Navigator) {
    val seriesAgrupadas = listaSeries.groupBy { it.idEjercicio } // Agrupa por idEjercicio

    LazyColumn(modifier = Modifier.background(Color.Black)) {
        seriesAgrupadas.forEach { (idEjercicio, series) ->
            val eje = rutinasViewModel.listaAllExercises.find { it.id == idEjercicio }!!

            // Título del ejercicio
            item(key = "titulo_$idEjercicio") {
                Card(
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    backgroundColor = Color.Black
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val imgExercise = painterResource(Res.drawable.press_banca)
                        Image(
                            painter = imgExercise,
                            contentDescription = "imageExercise",
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            eje.nombre,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }

            // Items de los sets
            items(series, key = { it.id }) { s ->
                ItemSetStartRoutine(s, rutinasViewModel)
            }
            item {
                ButtonAddSet(rutinasViewModel, eje.id, rutinasViewModel.idRutinaSeleccionada)
                Divider(modifier = Modifier.height(2.dp), color = Color.Red)
            }

        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonAddEjercicio(nav)
                Spacer(modifier = Modifier.width(10.dp))
                ButtonDiscardWorkout(rutinasViewModel)
            }
            Spacer(modifier = Modifier.height(400.dp))
        }
    }
}

@Composable
fun ButtonDiscardWorkout(rutinasViewModel: RutinasViewModel) {
    Button(
        modifier = Modifier.padding(6.dp),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, colorRed),
        onClick = {
            rutinasViewModel.onClickDiscardWorkout()
        }) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Descartar\nentrenamiento", color = Color(0Xffdf3d3d))
            Spacer(modifier = Modifier.width(5.dp))
            Icon(Icons.Default.Cancel, contentDescription = "cancelIcon", tint = Color.White)
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ItemSetStartRoutine(s: Serie, rutinasViewModel: RutinasViewModel) {
    var weight by remember {
        mutableStateOf(
            if (s.weight % 1.0 == 0.0) s.weight.toInt()
                .toString() else s.weight.toString()
        )
    }
    var reps by remember { mutableStateOf(s.reps.toString()) }

    val modifierTextFiled =
        Modifier.width(100.dp).height(80.dp).padding(8.dp)

    SwipeToDeleteSet(
        item = s,
        onDelete = { rutinasViewModel.deleteSerieByListaUI(it) }
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = if (s.isChecked.value) Color(0xff066800) else Color.Black
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                NativeTextField(
                    valor = weight,
                    onValueChange = {
                        weight = it
                        rutinasViewModel.onValueChangeSerie(s.id, weight, reps)
                    },
                    label = "KG",
                    modifier = modifierTextFiled)


//                CustomTextField(
//                weight,
//                {
//                    weight = it
//                    rutinasViewModel.onValueChangeSerie(s.id, weight, reps)
//                },
//                "KG",
//                modifierTextFiled)

                Spacer(modifier = Modifier.width(15.dp))

                NativeTextField(
                    valor = reps,
                    onValueChange = {
                        reps = it
                        rutinasViewModel.onValueChangeSerie(s.id, weight, reps)
                    },
                    label = "REPS",
                    modifier = modifierTextFiled)
//                CustomTextField(
//                    reps,
//                    {
//                    reps = it
//                    rutinasViewModel.onValueChangeSerie(s.id, weight, reps)
//                },
//                    "REPS",
//                    modifierTextFiled)

                Spacer(modifier = Modifier.width(15.dp))

                Icon(
                    Icons.Default.CheckCircle,
                    modifier = Modifier.clickable {

                        s.isChecked.value = !s.isChecked.value
                        rutinasViewModel.modificarSerieChecked(s)
                    },
                    contentDescription = "checkIcon",
                    tint = if (s.isChecked.value) Color(0xff0de000) else Color.Gray
                )
            }
        }
    }
}