package org.basicfactorysm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.zumba
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Clase
import org.basicfactorysm.presentacion.ClasesViewModel
import org.basicfactorysm.utils.ClasesUIState
import org.basicfactorysm.utils.Dia
import org.basicfactorysm.utils.backgroundApp
import org.basicfactorysm.utils.colorRed
import org.basicfactorysm.utils.styleItemDayWeek
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClasesScreen(nav: Navigator, clasesViewmodel: ClasesViewModel) {

    Scaffold(
        content = { paddingValues -> BodyClases(paddingValues, clasesViewmodel) },
        topBar = { ToolbarClases(nav) }
    )
}

@Composable
fun ToolbarClases(nav: Navigator) {
    TopAppBar(
        title = { Text("Clases Colectivas") },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(40.dp).clickable { nav.goBack() }
            )
        }
    )
}

@Composable
fun BodyClases(paddingValues: PaddingValues, clasesViewmodel: ClasesViewModel) {
    if (clasesViewmodel.showDialogConfirm.value) {
        DialogConfirm(clasesViewmodel)
    }
    Box(
        modifier = Modifier.fillMaxSize().background(backgroundApp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ListDaysWeek(clasesViewmodel)
            Spacer(modifier = Modifier.height(10.dp))
            UIStateClases(clasesViewmodel)
        }
    }
}

@Composable
fun ListDaysWeek(clasesViewModel: ClasesViewModel) {
    val listDayWeek = clasesViewModel.listDayWeek
    Box(
        modifier = Modifier.fillMaxWidth(),
        //.background(Color.Black, shape = RoundedCornerShape(15.dp))
        //.border(2.dp, backgroundApp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            LazyRow {
                items(listDayWeek) {
                    DayItem(it, clasesViewModel)
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.LightGray)
            )
        }

    }
}

@Composable
fun DayItem(dia: Dia, clasesViewmodel: ClasesViewModel) {

    val modifierBox = Modifier
        .fillMaxWidth()
        .padding(6.dp)
        //.clip(RoundedCornerShape(20.dp))
        .let {
            if (!dia.clicado) it.clickable { clasesViewmodel.onClickDayWeek(dia.fecha) } else it
        }

    Box(modifier = modifierBox, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
        ) {
            val dayWeek = dia.diaSemana.substring(0, 3)
            Text(dayWeek, fontSize = 12.sp, color = Color.White)
            Box(
                modifier = Modifier.background(
                    if (dia.clicado) Color(0xFFee5656) else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                )
            ) {
                Text(
                    dia.d.toString(),
                    style = styleItemDayWeek,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }
}

@Composable
fun UIStateClases(clasesViewmodel: ClasesViewModel) {

    val uiState by clasesViewmodel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is ClasesUIState.Error -> ListaClasesError((uiState as ClasesUIState.Error).message)
        ClasesUIState.Loading -> LoadingListaClases()
        is ClasesUIState.Success -> ListaClasesSuccess(
            (uiState as ClasesUIState.Success).listaClases,
            clasesViewmodel
        )

    }
}

@Composable
fun LoadingListaClases() {
    Box {
        Text(
            "Cargando...", fontSize = TextUnit(18f, TextUnitType.Sp),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
        )
    }
}

@Composable
fun ListaClasesError(msjError: String) {
    Box {
        Text(
            "Error al visualizar las clases: $msjError", fontSize = TextUnit(18f, TextUnitType.Sp),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
        )
    }
}

@Composable
fun ListaClasesSuccess(listaClases: List<Clase>, clasesViewmodel: ClasesViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(listaClases) {
                ItemClase(clase = it, clasesViewmodel)
            }
        }
    }
}

@Composable
fun ItemClase(clase: Clase, clasesViewmodel: ClasesViewModel) {

    val painter = painterResource(Res.drawable.zumba)

    Box(
        modifier = Modifier.fillMaxWidth()
            .height(130.dp)
            .padding(top = 8.dp).padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(40.dp))
            //.paint(painter = painter, contentScale = ContentScale.FillBounds)
            .border(2.dp, colorRed, shape = RoundedCornerShape(40.dp))
            .clickable { clasesViewmodel.onClickClase(clase) },
        contentAlignment = Alignment.Center
    ) {

        /*Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    clase.horario, fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    clase.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White,
                )

                Text(
                    text = clase.monitor, fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start).padding(start = 30.dp)
                )


            }

            Column(horizontalAlignment = Alignment.End) {
                Text("Marcar como hecho!", color = Color.White)

            }
        }*/
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                clase.nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White,
            )
            Text(
                clase.sala, fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.White
            )

            Text(
                clase.horario, fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )

            val apuntados = clase.apuntados
            val limite = clase.limite


            Text(
                text = "$apuntados/$limite", color = Color.White, fontSize = 14.sp
            )
        }

        Box(
            modifier = Modifier.align(Alignment.BottomStart)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = clase.monitor, fontSize = 16.sp,
                color = Color.White,
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}