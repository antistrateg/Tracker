package ru.developmentmobile.tracker.map.presentation.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_map.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.extension.isNull
import ru.developmentmobile.tracker.map.presentation.ui.MapFragment.Section
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapUiEvents

class MapActivity : AppCompatActivity() {

    private val viewModel by viewModel<MapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        if (savedInstanceState.isNull()) {
            postEvent(MapUiEvents.CreateSection(
                Section.convertIdToEnumSection(DESTINATION_SECTION_VALUE))
            )
            navigationView.selectedItemId = DESTINATION_SECTION_VALUE
        } else {
            val section = savedInstanceState!!.getInt(DESTINATION_SECTION_NAME,DESTINATION_SECTION_VALUE)
            postEvent(MapUiEvents.CreateSection(Section.convertIdToEnumSection(section)))
        }
        navigationView.setOnNavigationItemSelectedListener { item ->
            postEvent(MapUiEvents.CreateSection(Section.convertIdToEnumSection(item.itemId)))
            DESTINATION_SECTION_VALUE = item.itemId
            return@setOnNavigationItemSelectedListener true
        }

    }

    private fun postEvent(event: MapUiEvents) = viewModel.uiEvents.postValue(event)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(DESTINATION_SECTION_NAME, DESTINATION_SECTION_VALUE);
    }

    override fun onDestroy() {
        viewModelStore.clear()
        super.onDestroy()
    }

    companion object {
        private const val DESTINATION_SECTION_NAME = "DESTINATION"
        private var DESTINATION_SECTION_VALUE = Section.LOCATIONS.id
        @IdRes val MAP_NAVIGATION_ID = R.id.map_nav_graph
    }
}
