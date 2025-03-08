package org.basicfactorysm.ui.rutinas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.press_banca
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Serie
import org.basicfactorysm.navigation.Rutas
import org.basicfactorysm.presentacion.RutinasViewModel
import org.basicfactorysm.utils.SwipeToDeleteSet
import org.basicfactorysm.utils.colorRed
import org.jetbrains.compose.resources.painterResource

@Composable
fun EditRutinaScreen(nav: Navigator, rutinasViewModel: RutinasViewModel) {

    val listaSeries = rutinasViewModel.listaSeries
    val listaSeriesAux = rutinasViewModel.listaSeriesAux

    Column(modifier = Modifier.background(Color.Black)) {
        Box(modifier = Modifier.weight(0.08f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp).fillMaxSize().background(Color.White)
            ) {
                TextButton(onClick = {
                    if (listaSeries == listaSeriesAux) {
                        nav.goBack()
                    } else {
                        rutinasViewModel.showDialogSalirSinGuardar()
                    }
                }) {
                    Text("Cancelar", fontWeight = FontWeight.Bold)
                }
            }
        }

        if (rutinasViewModel.showCambiosPendientes.value) {
            DialogConfirmarSalidaSinGuardar(rutinasViewModel, nav)
        }
        Box(modifier = Modifier.weight(0.8f)) {
            //ListaEjerciciosEditRutina(listaEjercicios, rutinasViewModel)
            ListaSets(listaSeries, rutinasViewModel)
        }

        Box(modifier = Modifier.weight(0.1f)) {
            Divider(modifier = Modifier.fillMaxWidth().height(4.dp).background(Color.DarkGray))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonGuardarRutina(rutinasViewModel, nav)
                Spacer(modifier = Modifier.width(10.dp))
                ButtonAddEjercicio(nav)
            }
        }
    }
}

@Composable
fun DialogConfirmarSalidaSinGuardar(rutinasViewModel: RutinasViewModel, nav: Navigator) {
    AlertDialog(onDismissRequest = { rutinasViewModel.hideDialogSalirSinGuardar() },
        title = { Text("Salir sin guardar") },
        text = { Text("¿Estás seguro que desea salir sin guardar?") },
        confirmButton = {
            TextButton(onClick = {
                rutinasViewModel.onClickGuardarRutina()
                rutinasViewModel.hideDialogSalirSinGuardar()
                nav.goBack()
            }) {
                Text("Guardar antes de salir")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                nav.goBack()
                rutinasViewModel.volverAtrasSinGuardar()
            }) {
                Text("Salir sin guardar", color = Color.Red)
            }
        }
    )
}

@Composable
fun ListaSets(listaSeries: List<Serie>, rutinasViewModel: RutinasViewModel) {
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
                ItemSet(s, rutinasViewModel)
            }
            item {
                ButtonAddSet(rutinasViewModel, eje.id, rutinasViewModel.idRutinaSeleccionada)
                Divider(modifier = Modifier.height(2.dp), color = Color.Red)
            }
        }
    }
}

@Composable
fun ItemSet(s: Serie, rutinasViewModel: RutinasViewModel) {
    var weight by remember {
        mutableStateOf(
            if (s.weight % 1.0 == 0.0) s.weight.toInt()
                .toString() else s.weight.toString()
        )
    }
    var reps by remember { mutableStateOf(s.reps.toString()) }

    val modifierTextFiled =
        Modifier.width(100.dp).padding(8.dp)

    SwipeToDeleteSet(
        item = s,
        onDelete = { rutinasViewModel.deleteSerieByListaUI(it) }
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = if (s.isChecked.value) Color(0xff066800) else Color.Black
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                CustomTextField(weight, {
                    weight = it
                    rutinasViewModel.onValueChangeSerie(s.id, weight, reps)
                }, "KG", modifierTextFiled)

                Spacer(modifier = Modifier.width(15.dp))

                CustomTextField(reps, {
                    reps = it
                    rutinasViewModel.onValueChangeSerie(s.id, weight, reps)
                }, "REPS", modifierTextFiled)

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

@Composable
fun CustomTextField(
    valor: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifierTextFiled: Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(label, color = Color.White) },
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF00A6FF), // Azul al enfocar
            unfocusedBorderColor = Color.Gray, // Gris cuando no está enfocado
            cursorColor = Color(0xFF00A6FF), // Azul para el cursor
            textColor = Color.White,
            backgroundColor = Color(0x40FFFFFF) // Blanco con transparencia
        ),
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        modifier = modifierTextFiled
    )
}


@Composable
fun ButtonAddSet(rutinasViewModel: RutinasViewModel, idEjercicio: Int, idRutina: Int) {
    Button(
        modifier = Modifier.padding(15.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorRed
        ),
        onClick = { rutinasViewModel.addSetForListaUI(idEjercicio, idRutina) }) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Añadir set", color = Color.White)
            Spacer(modifier = Modifier.width(5.dp))
            Icon(Icons.Default.Add, contentDescription = "add", tint = Color.White)
        }
    }
}

@Composable
fun ButtonGuardarRutina(rutinasViewModel: RutinasViewModel, nav: Navigator) {
    Button(
        modifier = Modifier.padding(6.dp),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, colorRed),
        onClick = {
            rutinasViewModel.onClickGuardarRutina()
            nav.goBack()
        }) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Guardar cambios")
            Spacer(modifier = Modifier.width(5.dp))
            Icon(Icons.Default.Save, contentDescription = "saveIcon")
        }
    }
}

@Composable
fun ButtonAddEjercicio(navigator: Navigator) {
    Button(
        modifier = Modifier.padding(6.dp),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, colorRed),
        onClick = {
            navigator.navigate(Rutas.AddExercise.ruta)
        }) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Añadir ejercicio")
            Spacer(modifier = Modifier.width(5.dp))
            Icon(Icons.Default.Add, contentDescription = "addIcon", tint = Color.White)
        }
    }
}