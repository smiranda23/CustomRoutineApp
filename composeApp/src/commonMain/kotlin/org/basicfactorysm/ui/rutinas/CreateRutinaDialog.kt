package org.basicfactorysm.ui.rutinas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.basicfactorysm.presentacion.RutinasViewModel


@Composable
fun CreateRutinaDialog(rutinasViewModel: RutinasViewModel) {

    Dialog(onDismissRequest = { rutinasViewModel.hideDialogCreateRutina() }) {

        Box(
            modifier = Modifier.size(300.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                TextField(
                    singleLine = true,
                    value = rutinasViewModel.nombre,
                    onValueChange = { rutinasViewModel.nombre = it },
                    label = { Text("Nombre") })

                Spacer(modifier = Modifier.height(15.dp))

                Button(onClick = { rutinasViewModel.createRutina() }) {
                    Text("Crear rutina!")
                }
            }
        }
    }

}