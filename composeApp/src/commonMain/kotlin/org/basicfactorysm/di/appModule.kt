package org.basicfactorysm.di

import com.basicfactory.db.DatabaseBF
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.basicfactorysm.data.ClasesRepository
import org.basicfactorysm.data.GlobalRepository
import org.basicfactorysm.data.RutinaRepository
import org.basicfactorysm.data.TrainingRepository
import org.basicfactorysm.domain.ClaseRepInterface
import org.basicfactorysm.domain.IGlobalRepository
import org.basicfactorysm.domain.IRutinaRepository
import org.basicfactorysm.domain.ITrainingRepository
import org.basicfactorysm.presentacion.AccesViewModel
import org.basicfactorysm.presentacion.AccountViewModel
import org.basicfactorysm.presentacion.ClasesViewModel
import org.basicfactorysm.presentacion.EntrenadoresViewModel
import org.basicfactorysm.presentacion.HomeViewModel
import org.basicfactorysm.presentacion.RecetasViewModel
import org.basicfactorysm.presentacion.RutinasViewModel
import org.basicfactorysm.presentacion.TrainingViewModel
import org.koin.dsl.module

fun appModule(databaseBF: DatabaseBF) = module {
    single<HttpClient> { HttpClient { install(ContentNegotiation) { json() } } }
    single<IGlobalRepository> { GlobalRepository(get(), databaseBF) }
    single<IRutinaRepository> { RutinaRepository(databaseBF) }
    single<ITrainingRepository> { TrainingRepository(databaseBF) }

    factory { HomeViewModel() }
    factory { ClasesViewModel(get()) }
    factory { EntrenadoresViewModel(get()) }
    factory { AccountViewModel(get()) }
    factory { RutinasViewModel(get()) }
    factory { TrainingViewModel(get()) }
    factory { AccesViewModel(get()) }
    factory { RecetasViewModel(get()) }
}