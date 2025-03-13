package org.basicfactorysm.ui.nativo

import platform.UIKit.UIViewController

interface NativeTextViewFactory {
    fun createTextFieldView(
        label: String,
        onClick: () -> Unit
        //onValueChange: (String) -> Unit,
    ):UIViewController
}
