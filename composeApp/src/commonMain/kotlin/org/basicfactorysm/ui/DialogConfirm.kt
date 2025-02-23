package org.basicfactorysm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.basicfactorysm.presentacion.ClasesViewModel

@Composable
fun DialogConfirm(clasesViewModel: ClasesViewModel) {

    AlertDialog(
        modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)),
        onDismissRequest = { clasesViewModel.cerrarDialogConfirm() },
        title = { Text("Confirmar Reserva") },
        text = { Text("¿Desea reservar esta clase?") },
        confirmButton = {
            Button(onClick = { clasesViewModel.onclickReservaConfirmada() }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(onClick = { clasesViewModel.cerrarDialogConfirm() }) {
                Text("Cancelar")
            }
        }
    )

}