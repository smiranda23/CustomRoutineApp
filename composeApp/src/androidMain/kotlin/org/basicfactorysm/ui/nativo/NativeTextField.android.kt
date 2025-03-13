package org.basicfactorysm.ui.nativo

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
actual fun NativeTextField(
    //valor: String,
    //onValueChange: (String) -> Unit,
    //label:String,
    onClick:()->Unit,
    modifier: Modifier
) {
    Button(onClick=onClick, modifier = modifier) {
        Text("HolaaaAndroid")
    }

}