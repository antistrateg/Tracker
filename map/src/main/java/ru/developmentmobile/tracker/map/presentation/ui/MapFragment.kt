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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import ru.developmentmobile.tracker.map.presentation.router.MapRouter
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapUiModel
import java.util.*

class MapFragment : Fragment(), OnMapReadyCallback {

    private val router: MapRouter by inject()
    private val viewModel: MapViewModel by sharedViewModel()
    private val updateDataObserver = Observer<MapUiModel> { handleUiData(it) }

    private lateinit var googleMap: GoogleMap

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

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            this.googleMap = it
            configureGoogleMap()
        }
    }

    private fun configureGoogleMap() {
        val uiSettings = googleMap.uiSettings
        uiSettings.isZoomControlsEnabled = false
        uiSettings.isMyLocationButtonEnabled = true
        uiSettings.isCompassEnabled = true

        googleMap.setPadding(0, 0, 0, 32)
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f))
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLng(LatLng(DEFAULT_LOCATION_LAT, DEFAULT_LOCATION_LNG))
        )
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

    companion object {
        private const val REQUEST_CODE_PERMISSION_FINE_LOCATION = 1
        private const val DEFAULT_LOCATION_LAT: Double = 55.7561
        private const val DEFAULT_LOCATION_LNG: Double = 37.6186
    }


}