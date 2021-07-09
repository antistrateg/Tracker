package ru.developmentmobile.tracker.splash.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.developmentmobile.tracker.splash.R
import ru.developmentmobile.tracker.splash.presentation.router.SplashRouter
import ru.developmentmobile.tracker.splash.presentation.ui.viewmodels.SplashUiEvents
import ru.developmentmobile.tracker.splash.presentation.ui.viewmodels.SplashUiModel

class SplashFragment : Fragment() {

    private val router: SplashRouter by inject()
    private val viewModel: SplashViewModel by sharedViewModel()
    private val updateDataObserver = Observer<SplashUiModel> { handleUiData(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiData.observe(viewLifecycleOwner, updateDataObserver)

        postEvent(SplashUiEvents.StartSplashTimer(SPLASH_DELAY_TIME))
    }

    private fun handleUiData(data: SplashUiModel) {
        when (data) {
            is SplashUiModel.Data -> {
            }
            is SplashUiModel.Loading -> {
                if (data.isSplashVisible.not()) {
                    router.navigateNextToSplash(this)
                }
            }
            is SplashUiModel.Error -> {
            }
        }
    }

    private fun postEvent(event: SplashUiEvents) = viewModel.uiEvents.postValue(event)

    companion object {
        private const val SPLASH_DELAY_TIME: Long = 3000
    }
}