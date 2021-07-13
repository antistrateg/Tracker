package ru.developmentmobile.tracker.map.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import kotlinx.android.synthetic.main.fragment_map.*
import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapTrack
import ru.developmentmobile.tracker.map.extension.inflateSectionView
import ru.developmentmobile.tracker.map.presentation.router.MapRouter
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapUiEvents
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapUiModel
import java.util.*

class MapFragment : Fragment(), OnMapReadyCallback {

    private val router: MapRouter by inject()
    private val viewModel: MapViewModel by sharedViewModel()
    private val updateDataObserver = Observer<MapUiModel> { handleUiData(it) }

    lateinit var sectionView: View
    private lateinit var googleMap: GoogleMap
    private lateinit var doIfLocationPermissionGranted: () -> Unit

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

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.uiData.observe(viewLifecycleOwner, updateDataObserver)
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
        when (mapUiModel) {
            is MapUiModel.CreateSection -> {
                val section = mapUiModel.section
                sectionView = requireContext().inflateSectionView(section.layout, section_container)
                when (section) {
                    Section.TRACKS -> createSectionTracks()
                    Section.LOCATIONS -> createSectionLocations()
                    Section.BEACON -> createSectionBeacons()
                }
                postEvent(MapUiEvents.LoadSectionData(section))
            }
            is MapUiModel.LoadSectionData -> {
                when (mapUiModel.section) {
                    Section.TRACKS -> loadSectionDataTracks(mapUiModel.tracks)
                    Section.LOCATIONS ->
                        loadSectionDataLocations(mapUiModel.locations, mapUiModel.needToLoadMarkers)
                    Section.BEACON -> loadSectionDataBeacons(mapUiModel.beacons, mapUiModel.beacon)
                }
            }
        }
    }

    //================== CREATE ===========================================
    private fun createSectionTracks() {
        //TODO
    }

    private fun createSectionLocations() {
        //TODO
    }

    private fun createSectionBeacons() {
        //TODO
    }

    //================== LOAD SECTION DATA =================================
    private fun loadSectionDataTracks(tracks: List<MapTrack>) {

    }
    private fun loadSectionDataLocations(locations: List<MapLocation>, needToLoadMarkers: Boolean) {

    }
    private fun loadSectionDataBeacons(beacons: List<MapBeacon>, selected: MapBeacon? = null) {

    }


    private fun postEvent(event: MapUiEvents) = viewModel.uiEvents.postValue(event)

    private fun checkLocationPermission() {

        val permissionStatus =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            doIfLocationPermissionGranted()
        } else {
            requireActivity().requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_PERMISSION_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doIfLocationPermissionGranted()
                }
            }
        }
    }

    enum class Section(
        val id: Int, val layout: Int
    ) {
        TRACKS(R.id.tracks, R.layout.view_tracks),
        LOCATIONS(R.id.locations, R.layout.view_locations),
        BEACON(R.id.beacons, R.layout.view_beacons);

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