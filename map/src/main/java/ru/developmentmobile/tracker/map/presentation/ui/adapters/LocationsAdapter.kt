package ru.developmentmobile.tracker.map.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.view_locations_item.view.*
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.domain.model.MapLocation

class LocationsAdapter(
    private val itemClick: (locationId: Int) -> Unit,
    private val itemDelete: (locationId: Int) -> Unit
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    private var locations : List<MapLocation> = ArrayList()
    private var selected : MapLocation? = null

    override fun getItemViewType(position: Int) = R.layout.view_locations_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(locations[position], selected, itemClick, itemDelete)

    fun update(locations: List<MapLocation>, selected: MapLocation? = null) {
        this.locations = locations
        this.selected = selected
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            location: MapLocation,
            selected: MapLocation?,
            itemClick: (vehicleId: Int) -> Unit,
            itemDelete: (vehicleId: Int) -> Unit
        ) {

            with(view) {
                locationAddress.text = location.address
                locationCoordinates.text =
                        resources.getString(R.string.latitude_longitude,
                        location.location.latitude.toString(),
                            location.location.longitude.toString())
            }

            view.setOnClickListener { itemClick(location.id) }

            view.locationDeleteButton.setOnClickListener { itemDelete(location.id) }

        }


    }
}