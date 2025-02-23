package org.basicfactorysm.ui.rutinas

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import org.basicfactorysm.presentacion.RutinasViewModel

@Composable
fun ConfirmDeleteExerciseDialog(rutinasViewModel: RutinasViewModel){
    AlertDialog(onDismissRequest = {rutinasViewModel.hideConfirmDeleteExercise()},
        title = { Text("Eliminar ejercicio") },
        text = { Text("¿Estás seguro que desea eliminar este ejercicio?") },
        confirmButton = {
            TextButton(onClick = {rutinasViewModel.deleteExerciseAndSeries()}) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = {rutinasViewModel.hideConfirmDeleteExercise()}) {
                Text("Cancelar")
            }
        }
    )

}