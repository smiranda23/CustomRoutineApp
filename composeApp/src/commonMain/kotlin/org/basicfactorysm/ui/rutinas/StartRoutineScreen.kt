package org.basicfactorysm.ui.rutinas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.presentacion.RutinasViewModel

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
                ListaSets(listaSeries, rutinasViewModel)
            }
            Box(modifier = Modifier.weight(0.1f)) {
                Divider(modifier = Modifier.fillMaxWidth().height(4.dp).background(Color.DarkGray))
                ButtonAddEjercicio(navigator)
            }
        }
    }

    if (rutinasViewModel.showConfirmEndRoutine.value) {
        DialogConfirmEndRoutine(rutinasViewModel, navigator)
    }
    if (rutinasViewModel.showInfoRutinaSinValores.value) {
        DialogInfoRoutineSinValores(navigator, rutinasViewModel)
    }

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