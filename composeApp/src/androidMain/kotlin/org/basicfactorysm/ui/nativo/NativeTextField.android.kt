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
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
actual fun NativeTextField(
    valor: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier
) {

    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text(label, color = Color.White) },
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF00A6FF), // Azul al enfocar
            unfocusedBorderColor = Color.Gray, // Gris cuando no está enfocado
            cursorColor = Color(0xFF00A6FF), // Azul para el cursor
            textColor = Color.White,
            backgroundColor = Color(0x40FFFFFF) // Blanco con transparencia
        ),
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        modifier = modifier
    )
//    Button(onClick=onClick, modifier = modifier) {
//        Text("HolaaaAndroid")
//    }

}