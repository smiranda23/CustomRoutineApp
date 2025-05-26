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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.ic_campana
import basicfactorysm.composeapp.generated.resources.ic_news
import basicfactorysm.composeapp.generated.resources.ic_papel
import basicfactorysm.composeapp.generated.resources.ic_username
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.presentacion.AccountViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.utils.backgroundApp
import org.basicfactorysm.utils.colorRed
import org.jetbrains.compose.resources.painterResource


@Composable
fun AccountScreen(navigator: Navigator, cuentaViewModel: AccountViewModel) {

    Scaffold(
        content = { paddingValues -> BodyAccount(paddingValues) },
        topBar = { TopBarBasicFactory("Cuenta", navigator) }
    )
}

@Composable
fun BodyAccount(paddingValues: PaddingValues) {
    Box(modifier = Modifier.fillMaxSize().background(backgroundApp)) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp).verticalScroll(rememberScrollState())
        ) {
            Avatar()
            DatosPersonales()
            DatosContacto()
            DatosPago()
            //CamposEditables()
        }
    }


}

@Composable
fun Avatar() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.size(150.dp).clip(RoundedCornerShape(100.dp)),
            backgroundColor = Color.DarkGray,
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun DatosPersonales() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1B1B1F), Color(0xFF121212))
                )
            )
            .border(1.dp, colorRed.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = colorRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Datos Personales",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Divider(color = colorRed.copy(alpha = 0.3f), thickness = 1.dp)

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoDato(label = "Nombre y apellidos:", valor = "Sebastian Miranda")
                InfoDato(label = "Fecha de nacimiento:", valor = "06/10/1999")
            }
        }
    }

}

@Composable
fun InfoDato(label: String, valor: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.75f)
        )
        Text(
            text = valor,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun DatosContacto() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1B1B1F), Color(0xFF121212))
                )
            )
            .border(1.dp, colorRed.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    tint = colorRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Datos de Contacto",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Divider(color = colorRed.copy(alpha = 0.3f), thickness = 1.dp)

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoDato(label = "Dirección:", valor = "Carrer Sol i Padris 100")
                InfoDato(label = "Teléfono:", valor = "605 992 413")
            }
        }
    }
}


@Composable
fun DatosPago() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1B1B1F), Color(0xFF121212))
                )
            )
            .border(1.dp, colorRed.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Wallet, contentDescription = null,
                    tint = colorRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Datos de Pago",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Divider(color = colorRed.copy(alpha = 0.3f), thickness = 1.dp)

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoDato(label = "Número de cuenta:", valor = "************1738")
            }
        }
    }
}


@Composable
fun CamposEditables() {
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "605992413",
            onValueChange = { },
            label = { Text(text = "Móvil", color = Color.Black) },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                disabledTextColor = Color.Black
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "Carrer Sol i Padris",
            onValueChange = { },
            label = { Text(text = "Dirección", color = Color.Black) },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                disabledTextColor = Color.Black
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "smiranda9910@gmail.com",
            onValueChange = { },
            label = { Text(text = "Correo", color = Color.Black) },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                disabledTextColor = Color.Black
            ),
        )
    }
}

























