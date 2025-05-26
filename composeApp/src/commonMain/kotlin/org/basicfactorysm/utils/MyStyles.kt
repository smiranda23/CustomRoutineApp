package org.basicfactorysm.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val styleItemDayWeek = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = Color.White
)


//PANTALLA RUTINAS

//ItemSetDetails
val styleItemSetDetailsHeader = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    color = Color.White
)
val styleItemSetDetailsBody = TextStyle(
    fontSize = 14.sp,
    color = Color.White
)

//EDITAR RUTINA


//COLORES QUE APLICAN A TODAS LAS SCREENS (excepto login y settings, de momento)
val backgroundApp = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF121212),
        Color(0xFF1B1B1F),
        Color(0xFF1F1F23)
    )
)
val colorRed = Color(0xFF9f2413) //Rojo tirando a marron
val backgroundHome = Color.Black

//PANTALLA ENTRENADORES


//PANTALLA RECETAS

val styleTextCategoria = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    color = Color.DarkGray
)

val styleTextReceta = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = Color.White
)
