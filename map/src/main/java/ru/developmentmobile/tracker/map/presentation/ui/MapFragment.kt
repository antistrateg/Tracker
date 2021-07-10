package ru.developmentmobile.tracker.map.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.presentation.router.MapRouter
import java.util.*

class MapFragment : Fragment() {

    //private val router: MapRouter by inject()
    private val viewModel: MapViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)

    companion object {
    }
}