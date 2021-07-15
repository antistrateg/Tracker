package ru.developmentmobile.tracker.map.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.developmentmobile.tracker.map.presentation.ui.MapViewModel
import ru.developmentmobile.tracker.map.domain.interactor.MapBeaconInteractor
import ru.developmentmobile.tracker.map.domain.interactor.MapLocationInteractor
import ru.developmentmobile.tracker.map.domain.interactor.MapTrackInteractor

fun injectFeatureMap() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(viewModelModule, apiModule)
    )
}

val viewModelModule: Module = module {
    viewModel {
        MapViewModel(
            mapTrackInteractor = get(),
            mapLocationInteractor = get(),
            mapBeaconInteractor = get()
        )
    }
}

private val apiModule: Module = module {

    factory { MapTrackInteractor() }
    factory { MapLocationInteractor() }
    factory { MapBeaconInteractor() }

}




