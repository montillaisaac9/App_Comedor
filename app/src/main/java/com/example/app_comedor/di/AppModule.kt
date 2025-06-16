
package com.example.app_comedor.di

import com.example.app_comedor.data.repository.RepositoryAuthImp
import com.example.app_comedor.data.repository.RepositoryDishImp
import com.example.app_comedor.data.repository.RepositoryMenuImp
import com.example.app_comedor.domain.usecase.UseCase
import com.example.app_comedor.domain.usecase.modules.AuthUseCase
import com.example.app_comedor.domain.usecase.modules.DishUseCase
import com.example.app_comedor.domain.usecase.modules.MenuUseCase
import com.example.app_comedor.presentacion.screens.auth.login.LoginViewModel
import com.example.app_comedor.presentacion.screens.auth.register.RegisterViewModel
import com.example.app_comedor.presentacion.screens.auth.splash.SplashViewModel
import com.example.app_comedor.presentacion.screens.dish.DishViewModel
import com.example.app_comedor.presentacion.screens.menu.MenuViewModel
import com.example.app_comedor.presentacion.screens.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

// Módulo de repositorios
val repositoryModule = module {

    single<RepositoryAuthImp> { RepositoryAuthImp(get(), get()) }
    single<RepositoryMenuImp> { RepositoryMenuImp(get(), get(), get()) }
    single <RepositoryDishImp>{ RepositoryDishImp(get(), get(), get()) }

}

// Módulo de casos de uso
val useCaseModule = module {
    single { AuthUseCase(get<RepositoryAuthImp>()) }
    single { MenuUseCase(get<RepositoryMenuImp>()) }
    single { DishUseCase(get<RepositoryDishImp>()) }
    single { UseCase(
        auth = get(),
        menu = get(),
        dish = get()
    ) }
}

// Módulo de ViewModels
val viewModelModule = module {
    viewModel { LoginViewModel(get<UseCase>()) }
    viewModel { SplashViewModel(get<UseCase>()) }
    viewModel { RegisterViewModel(get<UseCase>()) }
    viewModel { MenuViewModel(get<UseCase>()) }
    viewModel { ProfileViewModel(get<UseCase>()) }
    viewModel { DishViewModel(get<UseCase>()) }
}


val AppModule = module {
    includes(repositoryModule, useCaseModule, viewModelModule)
}