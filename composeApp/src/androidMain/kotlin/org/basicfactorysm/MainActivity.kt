package org.basicfactorysm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.avena_porridge
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
fun Exemple() {

    Box(modifier = Modifier.fillMaxSize()){

        Box (modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Red).align(Alignment.TopEnd)){  }
        Box (modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Green).align(Alignment.Center)
        ){  }
        Box (modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Blue).align(Alignment.BottomEnd),
            ){

        }

    }


}

@Composable
fun PortadaTest() {
    Card(modifier = Modifier.fillMaxSize(), backgroundColor = Color.Green) { }

}