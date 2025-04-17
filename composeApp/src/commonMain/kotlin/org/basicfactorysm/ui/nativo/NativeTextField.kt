package org.basicfactorysm.ui.nativo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
expect fun NativeTextField(
    valor: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier)