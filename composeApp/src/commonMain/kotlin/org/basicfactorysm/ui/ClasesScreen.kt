package org.basicfactorysm.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Clase
import org.basicfactorysm.presentacion.ClasesViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.utils.ClasesUIState
import org.basicfactorysm.utils.Dia
import org.basicfactorysm.utils.backgroundApp
import org.basicfactorysm.utils.colorRed
import org.basicfactorysm.utils.styleItemDayWeek

@Composable
fun ClasesScreen(nav: Navigator, clasesViewmodel: ClasesViewModel) {

    Scaffold(
        content = { paddingValues -> BodyClases(paddingValues, clasesViewmodel) },
        topBar = { TopBarBasicFactory("Clases Colectivas", nav) }
    )
    DialogConfirm(clasesViewmodel, clasesViewmodel.showDialogConfirm.value, clasesViewmodel.claseSeleccionada)

}

@Composable
fun BodyClases(paddingValues: PaddingValues, clasesViewmodel: ClasesViewModel) {


    Box(
        modifier = Modifier.fillMaxSize().background(backgroundApp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            ListDaysWeek(clasesViewmodel)
            Spacer(modifier = Modifier.height(10.dp))
            UIStateClases(clasesViewmodel)
        }
    }
}

@Composable
fun ListDaysWeek(clasesViewModel: ClasesViewModel) {
    val listDayWeek = clasesViewModel.listDayWeek
    val fondoBox = Color(0xFF1B1B1F)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(fondoBox)
            .border(2.dp, Color(0xFF2A2A2F), shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(listDayWeek) {
                DayItem(it, clasesViewModel)
            }
        }
    }

}

@Composable
fun DayItem(dia: Dia, clasesViewmodel: ClasesViewModel) {
    val fondoSeleccionado by animateColorAsState(
        if (dia.clicado) colorRed else Color.Transparent
    )

    val modifierBox = Modifier
        .fillMaxWidth()
        .padding(6.dp)
        .let {
            if (!dia.clicado) it.clickable { clasesViewmodel.onClickDayWeek(dia.fecha) } else it
        }

    Box(
        modifier = modifierBox,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            val dayWeek = dia.diaSemana.take(3).uppercase()
            Text(
                text = dayWeek,
                fontSize = 12.sp,
                color = Color(0xFFDDDDDD),
                letterSpacing = 1.sp
            )

            Box(
                modifier = Modifier
                    .background(
                        fondoSeleccionado,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = dia.d.toString(),
                    style = styleItemDayWeek.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
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
    LazyColumn {
        items(listaClases) {
            ItemClase(clase = it, clasesViewmodel)
        }
    }
}

@Composable
fun ItemClase(clase: Clase, clasesViewmodel: ClasesViewModel) {

    val fondoBox = if(!clase.reservado) Color(0xFF1B1B1F) else Color.Green

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(fondoBox)
            .border(1.dp, colorRed, RoundedCornerShape(20.dp))
            .clickable {
                clasesViewmodel.onClickClase(clase)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = clase.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Text(
                        text = clase.sala,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
                Text(
                    text = "${clase.apuntados}/${clase.limite}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = colorRed,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = clase.horario,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }

                Text(
                    text = clase.monitor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}