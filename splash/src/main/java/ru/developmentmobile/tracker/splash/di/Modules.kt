package ru.developmentmobile.tracker.splash.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.developmentmobile.tracker.splash.presentation.ui.SplashViewModel

fun injectFeatureSplash() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule
    )
}

val viewModelModule: Module = module {
    viewModel { SplashViewModel() }
}
