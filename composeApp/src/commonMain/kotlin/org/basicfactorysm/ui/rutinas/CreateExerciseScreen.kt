package org.basicfactorysm.ui.rutinas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.presentacion.RutinasViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory

@Composable
fun CreateExerciseScreen(rutinasViewModel: RutinasViewModel, navigator: Navigator) {

    Box() {
        Column {
            TopBarBasicFactory("Crear Ejercicio", navigator)
            BodyCreateExercise(rutinasViewModel)
            ButtonCrearEjercicio(rutinasViewModel, navigator)
        }
    }

}

@Composable
fun ButtonCrearEjercicio(rutinasViewModel: RutinasViewModel, navigator: Navigator) {
    Button(onClick = {
        rutinasViewModel.onClickGuardarEjercicio()
        navigator.goBack()
    }) {
        Text("Guardar")
    }
}

@Composable
fun BodyCreateExercise(rutinasViewModel: RutinasViewModel) {

    var nombre = rutinasViewModel.nombreExercise

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = rutinasViewModel.nombreExercise,
        onValueChange = { rutinasViewModel.nombreExercise = it },
        label = { Text(text = "Nombre", color = Color.Black) },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            disabledTextColor = Color.Black
        ),
    )
}
