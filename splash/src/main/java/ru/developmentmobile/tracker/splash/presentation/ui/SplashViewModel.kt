package ru.developmentmobile.tracker.splash.presentation.ui

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.developmentmobile.tracker.splash.presentation.ui.viewmodels.SplashDataFactory
import ru.developmentmobile.tracker.splash.presentation.ui.viewmodels.SplashDataHolder
import ru.developmentmobile.tracker.splash.presentation.ui.viewmodels.SplashUiEvents
import ru.developmentmobile.tracker.splash.presentation.ui.viewmodels.SplashUiModel

class SplashViewModel: ViewModel() {

    private val factory = SplashDataFactory()

    private val cachedData = SplashDataHolder()

    private val uiEventsObserver: Observer<SplashUiEvents> = Observer { handleUiEvents(it) }

    private val uiDataMutable = MutableLiveData<SplashUiModel>()

    val uiEvents = MutableLiveData<SplashUiEvents>()

    val uiData: LiveData<SplashUiModel> = uiDataMutable

    init {
        uiEvents.observeForever(uiEventsObserver)
        //postValue()
    }

    private fun handleUiEvents(data: SplashUiEvents) {
        when (data) {
            is SplashUiEvents.StartSplashTimer -> {
                startSplashTimer(data.delayTime)
            }
        }
    }

    private fun startSplashTimer(delayTime: Long) {
        viewModelScope.launch {
            delay(delayTime)
            cachedData.isSplashVisible = false
            postValue()
        }
    }

    private fun postValue() = uiDataMutable.postValue(factory.createUiLoading(cachedData))
}
