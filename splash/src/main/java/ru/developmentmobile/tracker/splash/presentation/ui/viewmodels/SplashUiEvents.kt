package ru.developmentmobile.tracker.splash.presentation.ui.viewmodels

sealed class SplashUiEvents {
    data class StartSplashTimer(val delayTime: Long) : SplashUiEvents()
}