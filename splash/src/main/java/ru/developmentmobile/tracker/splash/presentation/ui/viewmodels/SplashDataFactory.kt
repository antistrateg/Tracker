package ru.developmentmobile.tracker.splash.presentation.ui.viewmodels

class SplashDataFactory {

    fun createUiLoading(data: SplashDataHolder): SplashUiModel.Loading =
        SplashUiModel.Loading(
            isSplashVisible = data.isSplashVisible
        )
}