package org.basicfactorysm.ui.nativo

import platform.UIKit.UIViewController

interface NativeTextViewFactory {
    fun createTextFieldView(
        valor: String,
        onValueChange: (String) -> Unit,
        label: String
    ):UIViewController
}
