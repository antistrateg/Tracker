package ru.developmentmobile.tracker.map.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.developmentmobile.tracker.map.data.cache.MapCacheDataSource
import ru.developmentmobile.tracker.map.data.remote.MapRemoteDataSource
import ru.developmentmobile.tracker.map.data.repository.MapRepositoryImpl
import ru.developmentmobile.tracker.map.datasourse.cache.MapCacheDataSourceImpl
import ru.developmentmobile.tracker.map.datasourse.remote.MapApi
import ru.developmentmobile.tracker.map.datasourse.remote.MapRemoteDataSourceImpl
import ru.developmentmobile.tracker.map.presentation.ui.MapViewModel
import ru.developmentmobile.tracker.map.domain.interactor.MapBeaconInteractor
import ru.developmentmobile.tracker.map.domain.interactor.MapLocationInteractor
import ru.developmentmobile.tracker.map.domain.interactor.MapTrackInteractor
import ru.developmentmobile.tracker.map.domain.repository.MapRepository
import ru.developmentmobile.tracker.map.network.NetworkClient

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

    factory { MapTrackInteractor(mapRepository = get()) }
    factory { MapLocationInteractor(mapRepository = get()) }
    factory { MapBeaconInteractor(mapRepository = get()) }

    factory <MapRepository> {
        MapRepositoryImpl(
            cacheDataSource = get(),
            remoteDataSource = get()
        )
    }

    single { NetworkClient() }

    factory <MapCacheDataSource> { MapCacheDataSourceImpl(mapCache = get()) }

    factory <MapRemoteDataSource> {
        val networkClient: NetworkClient = get()
        val api = networkClient.createClient(MapApi::class.java)
        MapRemoteDataSourceImpl(
            network = networkClient,
            api = api
        )
    }

}




