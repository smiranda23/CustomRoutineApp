package org.basicfactorysm

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.window.ComposeUIViewController
import com.basicfactory.db.DatabaseBF
import org.basicfactorysm.data.DatabaseDriverFactory
import org.basicfactorysm.di.appModule
import org.basicfactorysm.ui.nativo.NativeTextViewFactory
import org.koin.core.context.startKoin

val LocalNativeViewFactory = staticCompositionLocalOf<NativeTextViewFactory> {
    error("No view factory TEST provided.")
}

fun MainViewController(
    nativeTextViewFactory: NativeTextViewFactory
) = ComposeUIViewController {
    CompositionLocalProvider(LocalNativeViewFactory provides nativeTextViewFactory) {
        App()
    }
}

fun initKoin(){
    startKoin {
        modules(appModule(DatabaseBF.invoke(DatabaseDriverFactory().createDriver())))
    }.koin
}