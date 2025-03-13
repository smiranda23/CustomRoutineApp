package org.basicfactorysm.ui.nativo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun NativeTextField(
    //valor: String,
    //onValueChange: (String) -> Unit,
    //label: String,
    onClick:()->Unit,
    modifier: Modifier = Modifier)