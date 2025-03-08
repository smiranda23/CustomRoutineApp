package org.basicfactorysm.ui.rutinas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Gimnasio
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.press_banca
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Ejercicio
import org.basicfactorysm.model.Rutina
import org.basicfactorysm.model.Serie
import org.basicfactorysm.navigation.Rutas
import org.basicfactorysm.presentacion.RutinasViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.utils.backgroundApp
import org.basicfactorysm.utils.colorRed
import org.basicfactorysm.utils.styleItemSetDetailsBody
import org.basicfactorysm.utils.styleItemSetDetailsHeader
import org.jetbrains.compose.resources.painterResource

@Composable
fun RutinaDetallesScreen(navigator: Navigator, rutinasViewModel: RutinasViewModel) {

    val rutina = rutinasViewModel.rutinaSeleccionada
    val listaEjercicios = rutinasViewModel.listaEjerciciosByRutina

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarBasicFactory("Ejercicios", navigator)
            Box(modifier = Modifier.weight(0.3f)) {
                Portada(rutina)
            }

            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Column {
                    if (rutinasViewModel.timerStarted.value) {
                        TimerRoutine(rutinasViewModel)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ButtonStartRutina(navigator, rutinasViewModel)
                        Spacer(modifier = Modifier.width(5.dp))
                        ButtonEditRutina(navigator)
                    }
                }

            }
            Spacer(modifier = Modifier.height(5.dp))

            Box(modifier = Modifier.weight(0.7f)) {
                ListaEjercicios(listaEjercicios, rutinasViewModel)
            }
        }
    }

}

@Composable
fun ButtonStartRutina(navigator: Navigator, rutinasViewModel: RutinasViewModel) {
    Button(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, colorRed),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (rutinasViewModel.timerStarted.value) Color(0xffc6bb00) else Color(
                0xff28970d
            )
        ),
        onClick = {
            if (!rutinasViewModel.timerStarted.value) {
                rutinasViewModel.iniciarTimer()
                rutinasViewModel.setearCheckedMap()
            }
            navigator.navigate(Rutas.StartRoutine.ruta)
        }) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (rutinasViewModel.timerStarted.value) "En progreso..." else "Start Routine!",
                color = Color.White
            )
            Spacer(modifier = Modifier.width(5.dp))
            if (!rutinasViewModel.timerStarted.value) {
                Icon(Icons.Default.PlayCircle, contentDescription = "IconPlay", tint = Color.White)
            }
        }
    }
}

@Composable
fun ButtonEditRutina(navigator: Navigator) {
    Button(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, colorRed),
        onClick = {
            navigator.navigate(Rutas.EditRutina.ruta)
        }) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Editar Rutina")
            Spacer(modifier = Modifier.width(5.dp))
            Icon(Icons.Default.Edit, contentDescription = "addEdit")
        }
    }
}

@Composable
fun Portada(rutina: Rutina?) {
    val painterBasic = painterResource(Res.drawable.Gimnasio)

    Card(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.paint(painterBasic, contentScale = ContentScale.Crop)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    rutina!!.nombre,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            rutina!!.cantidadEjercicios.toString(),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Text("exercises", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(20.dp))
                    Divider(
                        modifier = Modifier.height(50.dp).width(1.dp).padding(vertical = 0.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(20.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            rutina!!.duracion.toString(), fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Text("minutos", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ListaEjercicios(listaEjercicios: List<Ejercicio>, rutinasViewModel: RutinasViewModel) {

    LazyColumn {
        items(listaEjercicios, key = { it.id }) {
            ItemEjercicio(it, rutinasViewModel)
        }
    }

}

@Composable
fun ItemEjercicio(eje: Ejercicio, rutinasViewModel: RutinasViewModel) {

    val listaSeries = rutinasViewModel.listaSeries.filter { it.idEjercicio == eje.id }

    Card(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Black
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imgExercise = painterResource(Res.drawable.press_banca)
            Image(
                painter = imgExercise,
                contentDescription = "imageExercise",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(eje.nombre, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(15.dp))
                Row {
                    Text("KG", style = styleItemSetDetailsHeader)
                    Spacer(modifier = Modifier.width(15.dp))
                    Text("REPS", style = styleItemSetDetailsHeader)
                }

                for (s in listaSeries) {
                    ItemSetDetails(s)
                }
            }
        }
    }
    Divider(
        modifier = Modifier.fillMaxSize().height(4.dp),
        color = colorRed
    )

}

@Composable
fun ItemSetDetails(s: Serie) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = if (s.weight % 1.0 == 0.0) s.weight.toInt()
                .toString() else s.weight.toString(),
            style = styleItemSetDetailsBody,
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(s.reps.toString(), style = styleItemSetDetailsBody)
    }
}



