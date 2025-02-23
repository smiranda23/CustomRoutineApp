package org.basicfactorysm.ui

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
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.ic_username
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Entrenador
import org.basicfactorysm.presentacion.EntrenadoresViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.utils.backgroundApp
import org.basicfactorysm.utils.colorRed
import org.jetbrains.compose.resources.painterResource

@Composable
fun EntrenadoresScreen(nav: Navigator, entrenadoresViewModel: EntrenadoresViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(backgroundApp)) {
        TopBarBasicFactory("Entrenadores", nav)
        ListaEntrenadores(entrenadoresViewModel)
    }
}

@Composable
fun ListaEntrenadores(entrenadoresViewModel: EntrenadoresViewModel) {
    val lista = entrenadoresViewModel.listaEntrenadores

    LazyColumn {
        items(lista) {
            ItemEntrenador(it)
        }
    }
}

@Composable
fun ItemEntrenador(entrenador: Entrenador) {

    val boxModifier = Modifier.fillMaxSize()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(50.dp)).padding(10.dp),
        backgroundColor = Color.Black
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = boxModifier
                    .weight(0.40f),
                contentAlignment = Alignment.Center
            ) {
                ImagenEntrenador()

            }

            Box(
                modifier = boxModifier
                    .weight(0.60f),
                contentAlignment = Alignment.Center
            ) {
                InfoEntrenador(entrenador)

            }
        }

    }


    /*Box(
        modifier = Modifier.fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(color = Color.DarkGray)
            .border(2.dp, colorRed, shape = RoundedCornerShape(40.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImagenEntrenador()
            Spacer(modifier = Modifier.width(10.dp))
            InfoEntrenador(entrenador)
        }
    }*/
}

@Composable
fun InfoEntrenador(entrenador: Entrenador) {
    Column {
        Text(
            entrenador.nombre,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(entrenador.correo, color = Color.White, fontSize = 14.sp)
        Text(entrenador.telefono, color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun ImagenEntrenador() {
    Card(
        modifier = Modifier.size(100.dp).clip(RoundedCornerShape(60.dp)),
        backgroundColor = Color.DarkGray,
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_username),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}