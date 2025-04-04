
package com.example.app_comedor.di

import com.example.app_comedor.data.repository.RepositoryAuthImp
import com.example.app_comedor.domain.repository.RepositoryAuth
import com.example.app_comedor.domain.usecase.UseCase
import com.example.app_comedor.domain.usecase.modules.AuthUseCase
import com.example.app_comedor.presentacion.screens.login.LoginViewModel
import com.example.app_comedor.presentacion.screens.register.RegisterViewModel
import com.example.app_comedor.presentacion.screens.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

// Módulo de repositorios
val repositoryModule = module {

    single<RepositoryAuthImp> { RepositoryAuthImp(get(), get()) }
}

// Módulo de casos de uso
val useCaseModule = module {
    single { AuthUseCase(get<RepositoryAuthImp>()) }

    single { UseCase(
        auth = get(),
    ) }
}

// Módulo de ViewModels
val viewModelModule = module {
    viewModel { LoginViewModel(get<UseCase>()) }
    viewModel { SplashViewModel(get<UseCase>()) }
    viewModel { RegisterViewModel(get<UseCase>()) }
}

// Módulo principal que incluye todos los otros módulos
val AppModule = module {
    includes(repositoryModule, useCaseModule, viewModelModule)
}