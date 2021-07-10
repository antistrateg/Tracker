package ru.developmentmobile.tracker.map.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.developmentmobile.tracker.map.R
import androidx.lifecycle.Observer
import ru.developmentmobile.tracker.map.presentation.router.MapRouter
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapUiModel
import java.util.*

class MapFragment : Fragment() {

    private val router: MapRouter by inject()
    private val viewModel: MapViewModel by sharedViewModel()
    private val updateDataObserver = Observer<MapUiModel> { handleUiData(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiData.observe(viewLifecycleOwner, updateDataObserver)

    }


    private fun handleUiData(mapUiModel: MapUiModel) {
//        w+hen (mapUiModel) {
//        }
    }

    enum class Section(
        val id: Int
    ) {
        TRACKS(R.id.tracks),
        LOCATIONS(R.id.locations),
        BEACON(R.id.beacons);

        companion object {
            fun convertIdToEnumSection(id: Int) =
                when (id) {
                    TRACKS.id -> TRACKS
                    LOCATIONS.id -> LOCATIONS
                    BEACON.id -> BEACON
                    else -> TRACKS
                }
        }
    }


}