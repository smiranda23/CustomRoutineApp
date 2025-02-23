package org.basicfactorysm

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.basicfactorysm.navigation.MyNavigation
import org.basicfactorysm.utils.AppTheme
import org.koin.compose.KoinContext

const val API_URL = "http://192.168.144.9:8080"

@Composable
fun App() {

    //Importante que las variables vayan dentro de precomposeapp, si no peta, nos pasó con 'nav'
    PreComposeApp {
        KoinContext {
            val nav = rememberNavigator()

            AppTheme {
                MyNavigation(nav)
            }
        }
    }
}