package org.basicfactorysm

import android.app.Application
import com.basicfactory.db.DatabaseBF
import org.basicfactorysm.data.DatabaseDriverFactory
import org.basicfactorysm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //Inicializamos koin en android
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            //Los modulos son lo que va a proveer de esa instancia
            modules(appModule(DatabaseBF.invoke(DatabaseDriverFactory(this@MainApplication).createDriver())))
        }

    }
}