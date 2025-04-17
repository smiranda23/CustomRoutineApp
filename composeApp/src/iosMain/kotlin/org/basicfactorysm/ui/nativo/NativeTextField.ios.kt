package org.basicfactorysm.ui.nativo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import org.basicfactorysm.LocalNativeViewFactory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
actual fun NativeTextField(
    valor: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier)
{

    val factory = LocalNativeViewFactory.current

    UIKitViewController(
      modifier = Modifier.width(125.dp).height(120.dp).padding(8.dp),
      factory = {
            factory.createTextFieldView(
                valor = valor,
                onValueChange = onValueChange,
                label = label)
      }
    )
}