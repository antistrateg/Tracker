package ru.developmentmobile.tracker.map.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


fun injectFeatureMap() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule
        //apiModule
    )
}

val viewModelModule: Module = module {

}

private val apiModule = module {

}
