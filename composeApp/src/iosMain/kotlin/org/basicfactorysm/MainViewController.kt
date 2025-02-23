package org.basicfactorysm

import androidx.compose.ui.window.ComposeUIViewController
import com.basicfactory.db.DatabaseBF
import org.basicfactorysm.data.DatabaseDriverFactory
import org.basicfactorysm.di.appModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin(){
    startKoin {
        modules(appModule(DatabaseBF.invoke(DatabaseDriverFactory().createDriver())))
    }.koin
}