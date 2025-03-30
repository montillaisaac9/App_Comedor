package com.example.app_comedor.app.android

import android.app.Application
import com.example.app_comedor.di.ApiModule
import com.example.app_comedor.di.AppModule
import com.example.app_comedor.di.RoomModule
import com.example.app_comedor.di.repositoryModule
import com.example.app_comedor.di.useCaseModule
import com.example.app_comedor.di.viewModelModule
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            slf4jLogger()
            modules(listOf(
                ApiModule,      // Módulo de servicios API
                repositoryModule, // Módulo de repositorios
                useCaseModule,    // Módulo de casos de uso
                viewModelModule,  // Módulo de ViewModels
                AppModule,        // Módulo principal que incluye a los demás
                RoomModule
            ))
        }
    }

}