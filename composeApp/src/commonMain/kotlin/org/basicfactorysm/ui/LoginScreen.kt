package org.basicfactorysm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.logo_factory
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.navigation.Rutas
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(nav: Navigator) {

    //Descartamos Scaffold ya que para pantalla de Login no lo veo atractivo

    /*Scaffold(
        content = { paddingValues -> container(paddingValues) },
        topBar = { toolbar() },
        containerColor = Color.Red
    )*/

    /*Card(
        Modifier
            .fillMaxHeight()
            .padding(12.dp),
        colors = CardDefaults.cardColors(Color.DarkGray)
    ) {
        Logo()
        Credenciales()
        Spacer(modifier = Modifier.weight(1f))
        BtnRegister()
    }*/


    Card(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Column(modifier = Modifier.background(Color.Black)) {
            Logo()
            Credenciales()
            BtnLogin(nav)
            Spacer(modifier = Modifier.weight(1f))
            BtnRegister()
        }
    }
}

@Composable
fun Logo() {
    val logoFactory = painterResource(Res.drawable.logo_factory)

    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = logoFactory,
            contentDescription = "Logo Factory",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 12.dp)
                .height(150.dp)
        )
    }
}


@Composable
fun Credenciales() {
    Column(
        Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "usuario",
            onValueChange = { },
            label = { Text(text = "DNI/NIF or Email", color = Color.White) },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.White,
                disabledTextColor = Color.White
            ),
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "password",
            onValueChange = {},
            label = { Text(text = "Password", color = Color.White) },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Switch(
                checked = false,
                onCheckedChange = { },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color.Green,
                )
            )
            Text(
                text = "Remem...",
                modifier = Modifier.padding(top = 12.dp, start = 4.dp),
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Need Help?",
                color = Color.White,
                modifier = Modifier.padding(top = 12.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun BtnLogin(nav: Navigator) {

    Button(
        onClick = { nav.navigate(Rutas.Home.ruta) },
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Text("Enter", color = Color.Black)
    }

}


@Composable
fun BtnRegister() {
    Button(
        onClick = {},
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        colors = ButtonDefaults.buttonColors(Color.Black)
    ) {
        Text("Register in the center", color = Color.White)
    }
}


