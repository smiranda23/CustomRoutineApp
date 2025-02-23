package org.basicfactorysm.utils

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
val backgroundApp = Color(0xFF211e1e) //Blanco tirando a gris/rojo
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
