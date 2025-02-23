package org.basicfactorysm.ui.recetas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.avena
import basicfactorysm.composeapp.generated.resources.avena_porridge
import basicfactorysm.composeapp.generated.resources.breakfast
import basicfactorysm.composeapp.generated.resources.comida
import basicfactorysm.composeapp.generated.resources.huevo
import basicfactorysm.composeapp.generated.resources.leche_almendra
import basicfactorysm.composeapp.generated.resources.postre
import basicfactorysm.composeapp.generated.resources.scoff_proteina
import basicfactorysm.composeapp.generated.resources.snacks
import basicfactorysm.composeapp.generated.resources.tortitas
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Ingrediente
import org.basicfactorysm.model.Receta
import org.basicfactorysm.presentacion.RecetasViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.utils.Categoria
import org.basicfactorysm.utils.backgroundApp
import org.basicfactorysm.utils.styleTextCategoria
import org.basicfactorysm.utils.styleTextReceta
import org.jetbrains.compose.resources.painterResource

@Composable
fun RecetasScreen(nav: Navigator, recetasViewModel: RecetasViewModel) {

    val paddingHorizontal = 20.dp

    Column(
        modifier = Modifier.fillMaxSize().background(backgroundApp)
    ) {

        TopBarBasicFactory("Recetas", nav)
        Box(
            modifier = Modifier.weight(0.15f)//.background(Color.Red)
                .padding(horizontal = paddingHorizontal),
            contentAlignment = Alignment.TopStart
        ) { Titulo() }
        Box(
            modifier = Modifier.weight(0.15f)//.background(Color.Green)
                .padding(start = paddingHorizontal),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    "Categorias",
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Categorias(recetasViewModel)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier.weight(0.25f)//.background(Color.Blue)
                .padding(horizontal = paddingHorizontal),
        ) {
            Column {
                Text("Platos", fontSize = 25.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                ListaRecetas(recetasViewModel, nav)
            }

        }
        Spacer(modifier = Modifier.height(10.dp))

        /*Box(
            modifier = Modifier.weight(0.4f)//.background(Color.Magenta)
                .padding(horizontal = paddingHorizontal),
        ) {
            DetallesReceta(recetasViewModel)
        }*/

    }
}


@Composable
fun Titulo() {
    Text(
        "¿Que te gustaría preparar hoy?",
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        lineHeight = 40.sp
    )
}

@Composable
fun Categorias(recetasViewModel: RecetasViewModel) {

    val listaCategorias = recetasViewModel.listaCategorias

    Spacer(modifier = Modifier.height(10.dp))
    LazyRow {
        items(listaCategorias) {
            ItemCategoria(it, recetasViewModel)
        }
    }
}

@Composable
fun ItemCategoria(c: Categoria, recetasViewModel: RecetasViewModel) {

    val icon = when (c.nombre) {

        "Desayunos" -> {
            Res.drawable.breakfast
        }

        "Comida" -> Res.drawable.comida
        "Postres" -> Res.drawable.postre
        "Cena" -> Res.drawable.comida
        "Snacks" -> Res.drawable.snacks
        else -> {
            Res.drawable.comida
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.background(
            color = if (c.clicado) Color(0xff1c8d26) else Color.White,
            shape = RoundedCornerShape(20.dp)
        ).clip(RoundedCornerShape(20.dp)).height(80.dp).width(100.dp)
            .clickable { recetasViewModel.onClickCategoria(c) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "iconCategoria",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                c.nombre,
                style = styleTextCategoria,
                color = if (c.clicado) Color.White else Color.DarkGray
            )
        }
    }
    Spacer(modifier = Modifier.width(10.dp))
}

@Composable
fun ListaRecetas(recetasViewModel: RecetasViewModel, nav: Navigator) {
    val listaRecetas = recetasViewModel.listaRecetas



    LazyRow {
        items(listaRecetas) {
            ItemReceta(it, recetasViewModel, nav)
        }
    }
}

@Composable
fun ItemReceta(receta: Receta, recetasViewModel: RecetasViewModel, nav: Navigator) {

    val imgReceta = painterResource(
        when (receta.nombre) {
            "Avena Proteica" -> Res.drawable.avena_porridge
            "Tortitas" -> Res.drawable.tortitas
            else -> Res.drawable.tortitas
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.background(
            color = Color.Transparent,
            shape = RoundedCornerShape(20.dp)
        ).clip(RoundedCornerShape(20.dp)).size(200.dp)
            .clickable { recetasViewModel.onClickReceta(receta, nav) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                imgReceta,
                contentDescription = "imgReceta",
                contentScale = ContentScale.Crop,
                modifier = Modifier.background(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White
                ).clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(receta.nombre, style = styleTextReceta)
        }
    }
    Spacer(modifier = Modifier.width(10.dp))
}


