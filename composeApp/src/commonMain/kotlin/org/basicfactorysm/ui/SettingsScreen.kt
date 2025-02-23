package org.basicfactorysm.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen() {
    BodySettings()
}

@Composable
fun BodySettings() {
    Column {
        ButtonCloseSession()
    }
}

@Composable
fun ButtonCloseSession() {
    IconButton(onClick = {}) {
        Icon(imageVector = Icons.Default.Close, contentDescription = null)
    }
}
