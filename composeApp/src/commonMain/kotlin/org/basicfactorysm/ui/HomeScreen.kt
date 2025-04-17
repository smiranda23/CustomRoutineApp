package org.basicfactorysm.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Accesos
import basicfactorysm.composeapp.generated.resources.ClasesColectivasV3
import basicfactorysm.composeapp.generated.resources.Cuenta
import basicfactorysm.composeapp.generated.resources.EntrenadorPersonal
import basicfactorysm.composeapp.generated.resources.Gimnasio
import basicfactorysm.composeapp.generated.resources.Recetas
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.RutinasV2
import basicfactorysm.composeapp.generated.resources.levantar_pesas
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.navigation.Rutas
import org.basicfactorysm.presentacion.HomeViewModel
import org.basicfactorysm.ui.nativo.NativeTextField
import org.basicfactorysm.utils.backgroundApp
import org.basicfactorysm.utils.backgroundHome
import org.basicfactorysm.utils.colorRed
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(nav: Navigator, homeViewModel: HomeViewModel) {
    Scaffold(
        content = { paddingValues -> ContenteHome(paddingValues, nav) },
        topBar = { Toolbar(nav, homeViewModel) }
    )
}

@Composable
fun Toolbar(navigator: Navigator, homeViewModel: HomeViewModel) {
    TopAppBar(
        title = { Text("Basic Factory Sabadell") },
        navigationIcon = {
            Icon(
                painter = painterResource(Res.drawable.levantar_pesas),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        },
        actions = {
            IconButton(onClick = { homeViewModel.onClickSettings(navigator) }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}

@Composable
fun ContenteHome(paddingValues: PaddingValues, nav: Navigator) {


    //NativeTextField("test",{},"labelTest", Modifier.width(150.dp).height(150.dp))

    Column(modifier = Modifier.fillMaxSize().background(backgroundApp)) {
        Box(modifier = Modifier.weight(0.20f)) {
            //CardNoticias()
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(modifier = Modifier.weight(0.80f)) {
            OpcionesPantallas(nav)
        }
    }


}

@Composable
fun CardNoticias() {
    Card(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        backgroundColor = colorRed,
        shape = RoundedCornerShape(20.dp)
    ) {

        Image(
            painter = painterResource(Res.drawable.Gimnasio),
            "",
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun OpcionesPantallas(nav: Navigator) {

    val fondoClasesColectivas = painterResource(Res.drawable.ClasesColectivasV3)
    val personalTrainer = painterResource(Res.drawable.EntrenadorPersonal)
    val backgroundAccesos = painterResource(Res.drawable.Accesos)
    val backgroundCuenta = painterResource(Res.drawable.Cuenta)
    val backgroundRecetas = painterResource(Res.drawable.Recetas)
    val backgroundRutinas = painterResource(Res.drawable.RutinasV2)

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            Section(
                fondoClasesColectivas,
                "Clases Colectivas"
            ) { nav.navigate(Rutas.ClasesColectivas.ruta) }

            Section(backgroundCuenta, "Cuenta") { nav.navigate(Rutas.Account.ruta) }
            Section(backgroundAccesos, "Accesos") { nav.navigate(Rutas.Accesos.ruta) }
        }

        Spacer(modifier = Modifier.width(40.dp))

        Column {
            Section(
                personalTrainer,
                "Entrenador Personal"
            ) { nav.navigate(Rutas.Entrenadores.ruta) }
            Section(backgroundRutinas, "Rutinas") { nav.navigate(Rutas.Rutinas.ruta) }
            Section(backgroundRecetas, "Recetas") { nav.navigate(Rutas.Recetas.ruta) }
        }
    }
}


@Composable
fun Section(painter: Painter, nombre: String, onSectionClick: () -> Unit) {

    Box(
        modifier = Modifier.padding(6.dp).size(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .paint(painter, contentScale = ContentScale.Crop)
            .border(2.dp, colorRed, shape = RoundedCornerShape(20.dp))
            .clickable { onSectionClick() },
        contentAlignment = Alignment.BottomStart,
    ) {
        Text(
            nombre,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(8.dp)
        )
    }
}