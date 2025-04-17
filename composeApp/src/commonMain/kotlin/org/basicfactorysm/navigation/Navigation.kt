package org.basicfactorysm.navigation

import RecetaDetalles
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.presentacion.AccesViewModel
import org.basicfactorysm.presentacion.AccountViewModel
import org.basicfactorysm.presentacion.ClasesViewModel
import org.basicfactorysm.presentacion.EntrenadoresViewModel
import org.basicfactorysm.presentacion.HomeViewModel
import org.basicfactorysm.presentacion.RecetasViewModel
import org.basicfactorysm.presentacion.RutinasViewModel
import org.basicfactorysm.presentacion.TrainingViewModel
import org.basicfactorysm.ui.AccesosScreen
import org.basicfactorysm.ui.AccountScreen
import org.basicfactorysm.ui.ClasesScreen
import org.basicfactorysm.ui.EntrenadoresScreen
import org.basicfactorysm.ui.HomeScreen
import org.basicfactorysm.ui.LoginScreen
import org.basicfactorysm.ui.SettingsScreen
import org.basicfactorysm.ui.recetas.RecetasScreen
import org.basicfactorysm.ui.rutinas.AddExerciseScreen
import org.basicfactorysm.ui.rutinas.CreateExerciseScreen
import org.basicfactorysm.ui.rutinas.EditRutinaScreen
import org.basicfactorysm.ui.rutinas.EntrenamientosScreen
import org.basicfactorysm.ui.rutinas.RutinaDetallesScreen
import org.basicfactorysm.ui.rutinas.RutinasScreen
import org.basicfactorysm.ui.rutinas.StartRoutineScreen
import org.basicfactorysm.ui.rutinas.TrainingDetailsScreen
import org.koin.core.parameter.parametersOf

@Composable
fun MyNavigation(nav: Navigator) {

    val claseViewmodel = koinViewModel(ClasesViewModel::class) { parametersOf() }
    val entrenadoresViewModel = koinViewModel(EntrenadoresViewModel::class) { parametersOf() }
    val cuentaViewModel = koinViewModel(AccountViewModel::class) { parametersOf() }
    val rutinasViewModel = koinViewModel(RutinasViewModel::class) { parametersOf() }
    val trainingViewModel = koinViewModel(TrainingViewModel::class) { parametersOf() }

    val accesViewModel = koinViewModel(AccesViewModel::class) { parametersOf() }
    val recetasViewModel = koinViewModel(RecetasViewModel::class) { parametersOf() }
    val homeViewModel = koinViewModel(HomeViewModel::class) { parametersOf() }

    NavHost(nav, initialRoute = Rutas.Login.ruta) {
        scene(Rutas.Login.ruta) {
            LoginScreen(nav)
        }
        scene(Rutas.Home.ruta) {
            HomeScreen(nav, homeViewModel)
        }
        scene(Rutas.ClasesColectivas.ruta) {
            ClasesScreen(nav, claseViewmodel)
        }
        scene(Rutas.Entrenadores.ruta) {
            EntrenadoresScreen(nav, entrenadoresViewModel)
        }
        scene(Rutas.Account.ruta) {
            AccountScreen(nav, cuentaViewModel)
        }
        scene(Rutas.Rutinas.ruta) {
            RutinasScreen(nav, rutinasViewModel,trainingViewModel)
        }
        scene(Rutas.Entrenamientos.ruta) {
            EntrenamientosScreen(nav, trainingViewModel,rutinasViewModel)
        }
        scene(Rutas.DetalleTraining.ruta) {
            TrainingDetailsScreen(nav, trainingViewModel)
        }
        scene(Rutas.StartRoutine.ruta) {
            StartRoutineScreen(nav, rutinasViewModel)
        }
        scene(Rutas.AddExercise.ruta) {
            AddExerciseScreen(rutinasViewModel, nav)
        }
        scene(Rutas.CreateExercise.ruta) {
            CreateExerciseScreen(rutinasViewModel, nav)
        }
        scene(Rutas.EditRutina.ruta) {
            EditRutinaScreen(nav, rutinasViewModel)
        }
        scene(Rutas.DetalleRutina.ruta) {
            RutinaDetallesScreen(nav, rutinasViewModel)
        }
        scene(Rutas.Accesos.ruta) {
            AccesosScreen(nav, accesViewModel)
        }
        scene(Rutas.Recetas.ruta) {
            RecetasScreen(nav, recetasViewModel)
        }
        scene(Rutas.DetalleReceta.ruta) {
            RecetaDetalles(nav, recetasViewModel)
        }
        scene(Rutas.Settings.ruta) {
            SettingsScreen()
        }
    }

}