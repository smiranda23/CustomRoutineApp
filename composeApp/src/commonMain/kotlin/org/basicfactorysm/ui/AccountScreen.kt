package org.basicfactorysm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.ic_campana
import basicfactorysm.composeapp.generated.resources.ic_news
import basicfactorysm.composeapp.generated.resources.ic_papel
import basicfactorysm.composeapp.generated.resources.ic_username
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.presentacion.AccountViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
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
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Avatar()
        DatosPersonales()
        DatosContacto()
        DatosPago()
        //CamposEditables()
    }

}

@Composable
fun Avatar() {

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.size(200.dp).clip(RoundedCornerShape(100.dp)),
            backgroundColor = Color.Red,
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_username),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }

}

@Composable
fun DatosPersonales() {

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(Res.drawable.ic_papel), contentDescription = null)
            Text("Datos Personales: ")
        }


        Row {
            Text(
                "Nombre y apellidos:",
                modifier = Modifier.padding(end = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text("Sebastián Miranda")
        }

        Row {
            Text(
                "Fecha de nacimiento:",
                modifier = Modifier.padding(end = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text("06/10/1999")
        }
    }
}

@Composable
fun DatosContacto() {

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(Res.drawable.ic_news), contentDescription = null)
            Text("Contacto: ")
        }

        Row {
            Text(
                "Dirección:",
                modifier = Modifier.padding(end = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text("Carrer Sol i Padris 100")
        }

        Row {
            Text(
                "Teléfono:",
                modifier = Modifier.padding(end = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text("605992413")
        }
    }
}

@Composable
fun DatosPago() {

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(Res.drawable.ic_campana), contentDescription = null)
            Text("Info bancaria:")
        }

        Row {
            Text(
                "Número de cuenta:",
                modifier = Modifier.padding(end = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text("**************1738")
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

























