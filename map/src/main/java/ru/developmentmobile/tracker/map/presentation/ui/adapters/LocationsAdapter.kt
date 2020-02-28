package ru.developmentmobile.tracker.map.presentation.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.view_locations_item.view.*
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.domain.model.MapLocation

class LocationsAdapter(
    private val itemClick: (locationId: Int) -> Unit,
    private val itemDelete: (locationId: Int) -> Unit
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    private var locations : List<MapLocation> = ArrayList()

    override fun getItemViewType(position: Int) = R.layout.view_locations_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(locations[position], itemClick, itemDelete)

    fun update(locations: List<MapLocation>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            location: MapLocation,
            itemClick: (vehicleId: Int) -> Unit,
            itemDelete: (vehicleId: Int) -> Unit
        ) {

            with(view) {
                locationAddress.text = location.address
                locationCoordinates.text =
                    "${location.id} | ${location.location.latitude} : ${location.location.longitude}"
            }

            view.setOnClickListener { itemClick(location.id) }

            view.locationDeleteButton.setOnClickListener { itemDelete(location.id) }

        }


    }
}