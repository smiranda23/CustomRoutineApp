package org.basicfactorysm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.avena_porridge
import basicfactorysm.composeapp.generated.resources.zumba
import org.basicfactorysm.model.Clase
import org.basicfactorysm.presentacion.ClasesViewModel
import org.basicfactorysm.utils.colorRed
import org.jetbrains.compose.resources.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

//@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Preview
@Composable
fun ClaseItemElegante(
    nombreClase: String = "Zumba",
    sala: String = "Sala 1",
    horario: String = "9:00 - 9:45",
    profesor: String = "Esther",
    apuntados: Int = 10,
    limite: Int = 30
) {
    val rojoElegante = Color(0xFF9f2413)
    val fondoBox = Color(0xFF1B1B1F)

    // Fondo general de la app (reemplaza al Color.Green que tenías)
    val fondoApp = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF121212),
            Color(0xFF1B1B1F),
            Color(0xFF1F1F23)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoApp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(fondoBox)
                .border(1.dp, rojoElegante.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .shadow(3.dp, RoundedCornerShape(16.dp), ambientColor = rojoElegante.copy(alpha = 0.2f))
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
                            text = nombreClase.uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = rojoElegante
                        )
                        Text(
                            text = sala,
                            fontSize = 13.sp,
                            color = Color(0xFFDDDDDD)
                        )
                    }
                    Text(
                        text = "$apuntados/$limite",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Divider(
                    color = rojoElegante.copy(alpha = 0.3f),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = Color(0xFFDDDDDD),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = horario,
                            fontSize = 13.sp,
                            color = Color(0xFFDDDDDD)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color(0xFFDDDDDD),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = profesor,
                            fontSize = 13.sp,
                            color = Color(0xFFDDDDDD)
                        )
                    }
                }
            }
        }
    }
}







@Composable
fun PortadaTest() {
    Card(modifier = Modifier.fillMaxSize(), backgroundColor = Color.Green) { }

}