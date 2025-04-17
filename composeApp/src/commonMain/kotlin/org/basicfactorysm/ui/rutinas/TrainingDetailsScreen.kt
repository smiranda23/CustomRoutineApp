package org.basicfactorysm.ui.rutinas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Gimnasio
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.press_banca
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.data.GlobalObject
import org.basicfactorysm.model.Rutina
import org.basicfactorysm.model.SerieFinalizada
import org.basicfactorysm.model.Training
import org.basicfactorysm.presentacion.RutinasViewModel
import org.basicfactorysm.presentacion.TrainingViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.ui.nativo.NativeTextField
import org.basicfactorysm.utils.SwipeToDeleteSet
import org.jetbrains.compose.resources.painterResource
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun TrainingDetailsScreen(navigator: Navigator, trainingViewModel: TrainingViewModel) {

    var training = trainingViewModel.trainSelected
    var listaSeriesTrainings = trainingViewModel.listaSeriesTraining

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarBasicFactory("Series realizadas", navigator)
            Box(modifier = Modifier.weight(0.3f)) {
                PortadaTraining(training)
            }

            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Column {


                }

            }
            Spacer(modifier = Modifier.height(5.dp))

            Box(modifier = Modifier.weight(0.7f)) {
                ListaSetsFinished(listaSeriesTrainings, trainingViewModel)
            }
        }
    }
}

@Composable
fun ListaSetsFinished(
    listSerieFinish: List<SerieFinalizada>,
    trainingViewModel: TrainingViewModel
) {
    val seriesFinaishedAgrupadas =
        listSerieFinish.groupBy { it.idEjercicio } // Agrupa por idEjercicio

    LazyColumn(modifier = Modifier.background(Color.Black)) {
        seriesFinaishedAgrupadas.forEach { (idEjercicio, series) ->
            val eje = GlobalObject.ListAllExercises.find { it.id == idEjercicio }!!

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
                ItemSetFinished(s, trainingViewModel)
            }
            item {
                Divider(modifier = Modifier.height(2.dp), color = Color.Red)
            }

        }
//        item {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                ButtonGuardarRutina(rutinasViewModel, nav)
//                Spacer(modifier = Modifier.width(10.dp))
//                ButtonAddEjercicio(nav)
//            }
//            Spacer(modifier = Modifier.height(400.dp))
//
//        }

    }

}

@Composable
fun ItemSetFinished(s: SerieFinalizada, trainingViewModel: TrainingViewModel) {
//    var weight by remember {
//        mutableStateOf(
//            if (s.weight % 1.0 == 0.0) s.weight.toInt()
//                .toString() else s.weight.toString()
//        )
//    }
//    var reps by remember { mutableStateOf(s.reps.toString()) }

    val modifierTextLabel =
        Modifier.padding(start = 8.dp)

    Card(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Black
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(6.dp)) {
            Text(
                "KG: ",
                modifier = modifierTextLabel,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Text(
                s.weight.toString(), fontSize = 16.sp
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                "REPS: ",
                modifier = modifierTextLabel,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Text(
                s.reps.toString(), fontSize = 16.sp
            )

            Spacer(modifier = Modifier.width(15.dp))

            Icon(imageVector = Icons.Default.Check, null, tint = Color.Green)
        }
    }

}

@Composable
fun PortadaTraining(training: Training?) {
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
                    training!!.name,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            training.setsFinished.toString(),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Text("sets", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(20.dp))
                    Divider(
                        modifier = Modifier.height(50.dp).width(1.dp).padding(vertical = 0.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(20.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            training.duration.toString(), fontWeight = FontWeight.ExtraBold,
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