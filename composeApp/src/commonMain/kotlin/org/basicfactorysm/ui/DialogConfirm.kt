package org.basicfactorysm.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.basicfactorysm.model.Clase
import org.basicfactorysm.presentacion.ClasesViewModel


@Composable
fun DialogConfirm(clasesViewModel: ClasesViewModel, visible: Boolean, clase: Clase?) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(initialScale = 0.9f),
        exit = fadeOut() + scaleOut(targetScale = 0.9f)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)) // Fondo oscurecido
                .clickable(onClick = { clasesViewModel.cerrarDialogConfirm() }, indication = null, interactionSource = remember { MutableInteractionSource() })
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF1B1B1F))
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Confirmar Reserva", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("¿Desea reservar esta clase?", color = Color.White.copy(alpha = 0.8f))

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = { clasesViewModel.onclickReservaConfirmada() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9f2413))
                        ) {
                            Text("Confirmar", color = Color.White)
                        }
                        OutlinedButton(onClick = { clasesViewModel.cerrarDialogConfirm() }) {
                            Text("Cancelar", color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}


//@Composable
//fun DialogConfirm(clasesViewModel: ClasesViewModel) {
//
//    AlertDialog(
//        modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)),
//        onDismissRequest = { clasesViewModel.cerrarDialogConfirm() },
//        title = { Text("Confirmar Reserva") },
//        text = { Text("¿Desea reservar esta clase?") },
//        confirmButton = {
//            Button(onClick = { clasesViewModel.onclickReservaConfirmada() }) {
//                Text("Confirmar")
//            }
//        },
//        dismissButton = {
//            Button(onClick = { clasesViewModel.cerrarDialogConfirm() }) {
//                Text("Cancelar")
//            }
//        }
//    )
//
//}