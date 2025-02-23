package org.basicfactorysm.ui.rutinas

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import org.basicfactorysm.presentacion.RutinasViewModel

@Composable
fun ConfirmDeleteRutinaDialog(rutinasViewModel: RutinasViewModel){

    AlertDialog(onDismissRequest = {rutinasViewModel.hideConfirmDeleteRutina()},
        title = { Text("Eliminar rutina") },
        text = { Text("¿Estás seguro que desea eliminar la rutina?") },
        confirmButton = {
            TextButton(onClick = {rutinasViewModel.deleteRutina(rutinasViewModel.rutinaSeleccionada!!.id)}) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = {rutinasViewModel.hideConfirmDeleteRutina()}) {
                Text("Cancelar")
            }
        }
    )
}