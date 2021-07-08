package ru.developmentmobile.tracker.splash.presentation.ui.viewmodels

sealed class SplashUiModel {

    object Data : SplashUiModel()

    data class Loading(
        var isSplashVisible: Boolean = true
    ) : SplashUiModel()

    object Error : SplashUiModel()
}