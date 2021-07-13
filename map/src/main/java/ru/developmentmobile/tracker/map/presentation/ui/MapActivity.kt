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

    private val viewModel: MapViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        if (savedInstanceState.isNull()) {
            postEvent(MapUiEvents.CreateSection(START_DESTINATION_SECTION))
            navigationView.selectedItemId = START_DESTINATION_SECTION.id
        }

        navigationView.setOnNavigationItemSelectedListener { item ->
            postEvent(MapUiEvents.CreateSection(Section.convertIdToEnumSection(item.itemId)))
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun postEvent(event: MapUiEvents) = viewModel.uiEvents.postValue(event)

    companion object {
        @IdRes val MAP_NAVIGATION_ID = R.id.map_nav_graph
        private val START_DESTINATION_SECTION = Section.LOCATIONS
    }
}
