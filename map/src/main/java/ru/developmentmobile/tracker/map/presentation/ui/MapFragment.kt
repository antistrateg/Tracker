package ru.developmentmobile.tracker.map.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.view_beacons.view.*
import kotlinx.android.synthetic.main.view_locations.view.*
import kotlinx.android.synthetic.main.view_tracks.view.*
import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapPoint
import ru.developmentmobile.tracker.map.domain.model.MapTrack
import ru.developmentmobile.tracker.map.extension.inflateSectionView
import ru.developmentmobile.tracker.map.presentation.router.MapRouter
import ru.developmentmobile.tracker.map.presentation.ui.adapters.BeaconsAdapter
import ru.developmentmobile.tracker.map.presentation.ui.adapters.LocationsAdapter
import ru.developmentmobile.tracker.map.presentation.ui.adapters.TracksAdapter
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapUiEvents
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapUiModel
import java.io.IOException
import java.util.*

class MapFragment : Fragment(), OnMapReadyCallback {

    private val router: MapRouter by inject()
    private val viewModel by sharedViewModel<MapViewModel>()
    private val updateDataObserver = Observer<MapUiModel> { handleUiData(it) }

    private lateinit var sectionView: View
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
        Log.d("TAG", mapUiModel.toString())
        when (mapUiModel) {
            is MapUiModel.CreateSection -> {
                googleMap.clear()
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
            is MapUiModel.ClickTrackItem -> {
                mapUiModel.track?.let { clickTrackItem(it) }
            }
            is MapUiModel.ClickLocationItem -> {
                mapUiModel.location?.let { clickLocationItem(it) }
            }
            is MapUiModel.ClickBeaconItem -> {
                mapUiModel.beacon?.let { clickBeaconItem(it) }
            }
            is MapUiModel.UpdateLocation -> {
                mapUiModel.location?.let { updateLocation(it) }
            }
            is MapUiModel.UpdateBeacon -> {
                mapUiModel.beacon?.let { updateBeacon(it) }
            }
            is MapUiModel.BeaconObserveStopped -> {
                beaconObserveStopped()
            }

            is MapUiModel.ShowProgressSectionData -> {
                when (mapUiModel.section) {
                    Section.TRACKS -> {
                        if (mapUiModel.isLoading)
                            sectionView.tracksShimmerLoader.visibility = View.VISIBLE
                        else sectionView.tracksShimmerLoader.visibility = View.GONE
                    }
                    Section.LOCATIONS -> {
                        if (mapUiModel.isLoading)
                            sectionView.locationsShimmerLoader.visibility = View.VISIBLE
                        else sectionView.locationsShimmerLoader.visibility = View.GONE
                    }
                    Section.BEACON -> {
                        if (mapUiModel.isLoading)
                            sectionView.beaconsShimmerLoader.visibility = View.VISIBLE
                        else sectionView.beaconsShimmerLoader.visibility = View.GONE
                    }
                }
            }
            is MapUiModel.ShowProgressAddSingleLocation -> {
                showProgressAddSingleLocation(mapUiModel.isLoading)
            }
            //is MapUiModel.Error -> { }
        }

    }

    //================== CREATE ===========================================
    private fun createSectionTracks() {
        sectionView.tracksRecycler.adapter = TracksAdapter(
            { track -> postEvent(MapUiEvents.ClickTrackItem(track)) },
            { track -> postEvent(MapUiEvents.DeleteTrack(track)) }
        )
        sectionView.tracksDeleteButton.setOnClickListener { postEvent(MapUiEvents.DeleteTracks) }
        sectionView.recordTrackButton.setOnClickListener {
            doIfLocationPermissionGranted = { recordTrack() }
            checkLocationPermission()
        }
    }

    private fun createSectionLocations() {
        sectionView.locationsRecycler.adapter = LocationsAdapter(
            { locationId -> postEvent(MapUiEvents.ClickLocationItem(locationId)) },
            { locationId -> postEvent(MapUiEvents.DeleteLocation(locationId)) }
        )
        sectionView.locationsDeleteButton.setOnClickListener { postEvent(MapUiEvents.DeleteLocations) }
        sectionView.addSingleLocationButton.setOnClickListener {
            doIfLocationPermissionGranted = { addSingleLocation() }
            checkLocationPermission()
        }
    }

    private fun createSectionBeacons() {
        sectionView.beaconsRecycler.adapter =
            BeaconsAdapter { beacon, isSelected ->
                if (isSelected.not()) postEvent(MapUiEvents.ClickBeaconItem(beacon))
            }
        sectionView.observeBeaconButton.setOnClickListener {
            postEvent(MapUiEvents.ObserveBeacon)
        }
    }

    //================== LOAD SECTION DATA =================================
    private fun loadSectionDataTracks(tracks: List<MapTrack>) {
        (sectionView.tracksRecycler.adapter as TracksAdapter).update(tracks)
        if (tracks.isNotEmpty()) {
            sectionView.tracksInformation.text =
                resources.getString(R.string.tracks_amount, tracks.size)
            sectionView.tracksDeleteButton.visibility = View.VISIBLE
            sectionView.tracksRecyclerFrame.visibility = View.VISIBLE
        } else {
            sectionView.tracksInformation.text = resources.getString(R.string.no_tracks)
            sectionView.tracksDeleteButton.visibility = View.GONE
            sectionView.tracksRecyclerFrame.visibility = View.GONE
        }
        //запись трека
    }

    private fun loadSectionDataLocations(locations: List<MapLocation>, needToLoadMarkers: Boolean) {
        Log.d("TAG", locations.toString())
        (sectionView.locationsRecycler.adapter as LocationsAdapter).update(locations)
        if (locations.isNotEmpty()) {
            sectionView.locationsInformation.text =
                resources.getString(R.string.locations_amount, locations.size)
            sectionView.locationsDeleteButton.visibility = View.VISIBLE
            sectionView.locationsRecyclerFrame.visibility = View.VISIBLE
        } else {
            sectionView.locationsInformation.text = resources.getString(R.string.no_locations)
            sectionView.locationsDeleteButton.visibility = View.GONE
            sectionView.locationsRecyclerFrame.visibility = View.GONE
        }
        if (needToLoadMarkers) {
            googleMap.clear()
            for (location in locations)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(location.location.latitude, location.location.longitude))
                        .title(location.address)
                )
        }
    }

    private fun loadSectionDataBeacons(beacons: List<MapBeacon>, selected: MapBeacon? = null) {
        (sectionView.beaconsRecycler.adapter as BeaconsAdapter).update(beacons, selected)
        selected?.let {
            sectionView.observeBeaconButton.setColorFilter(
                requireContext().getColor(R.color.colorPrimary)
            )
        }
        if (beacons.isNotEmpty()) {
            sectionView.beaconsInformation.text =
                resources.getString(R.string.beacons_amount, beacons.size)
            sectionView.beaconsRecyclerFrame.visibility = View.VISIBLE
        } else {
            sectionView.beaconsInformation.text = resources.getString(R.string.no_beacons)
            sectionView.beaconsRecyclerFrame.visibility = View.GONE
        }
    }
    //===============================================================
    private fun recordTrack() {
    }

    private fun addSingleLocation() {
        val target = googleMap.cameraPosition.target
        postEvent(
            MapUiEvents.AddSingleLocation(
                MapLocation(
                    location = MapPoint(target.latitude, target.longitude),
                    address = getAddressByTarget(target)
                )
            )
        )
    }

    //====== click item ==================================================
    private fun clickTrackItem(track: MapTrack) {
        BottomSheetBehavior.from(sectionView.tracksBehaviorLayout).state =
            BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun clickLocationItem(location: MapLocation) {
        BottomSheetBehavior.from(sectionView.locationsBehaviorLayout).state =
            BottomSheetBehavior.STATE_COLLAPSED
        val target = LatLng(location.location.latitude, location.location.longitude)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(target))
    }

    private fun clickBeaconItem(beacon: MapBeacon) {
        sectionView.observeBeaconButton.setColorFilter(
            requireContext().getColor(R.color.colorPrimary)
        )
        (sectionView.beaconsRecycler.adapter as BeaconsAdapter).update(beacon)
    }

    private fun updateLocation(location: MapLocation) {
        val target = LatLng(location.location.latitude, location.location.longitude)
        googleMap.addMarker(MarkerOptions().position(target).title(location.address))
    }

    private fun updateBeacon(beacon: MapBeacon) {
        sectionView.beaconTarget.visibility = View.VISIBLE
        sectionView.observeBeaconButton.setColorFilter(
            requireContext().getColor(R.color.colorRed)
        )
        val target = LatLng(beacon.location.latitude, beacon.location.longitude)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(target, 16f))
    }

    private fun beaconObserveStopped() {
        sectionView.beaconTarget.visibility = View.GONE
        sectionView.observeBeaconButton.setColorFilter(
            requireContext().getColor(R.color.colorPrimary)
        )
    }

    private fun showProgressAddSingleLocation(isLoading: Boolean) {
        sectionView.addSingleLocationButton.isEnabled = isLoading.not()
        if (isLoading) {
            sectionView.addSingleLocationProgressBar.visibility = View.VISIBLE
        } else {
            sectionView.addSingleLocationProgressBar.visibility = View.INVISIBLE
        }
    }

    private fun getAddressByTarget(target: LatLng): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(target.latitude, target.longitude, 1)
            return addresses[0].getAddressLine(0)
        } catch (e: IOException) {
            e.printStackTrace()
            return resources.getString(R.string.address)
        }
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