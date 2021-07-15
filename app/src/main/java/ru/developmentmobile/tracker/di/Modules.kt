package ru.developmentmobile.tracker.di

import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.developmentmobile.tracker.cache.di.cacheModule
import ru.developmentmobile.tracker.map.presentation.router.MapRouter
import ru.developmentmobile.tracker.router.map.MapRouterImpl
import ru.developmentmobile.tracker.router.splash.SplashRouterImpl
import ru.developmentmobile.tracker.splash.presentation.router.SplashRouter

fun injectApp() = load

private val load by lazy {
    loadKoinModules(
        listOf(router, cacheModule)
    )
}

val router: Module = module {
    factory { SplashRouterImpl() } bind SplashRouter::class
    factory { MapRouterImpl() } bind MapRouter::class
}
