package ru.developmentmobile.tracker.map.presentation.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_beacons_item.view.*
import kotlinx.android.synthetic.main.view_locations_item.view.*
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapLocation

class BeaconsAdapter(
    private val itemClick: (beacon: MapBeacon) -> Unit
) : RecyclerView.Adapter<BeaconsAdapter.ViewHolder>() {

    private var beacons : List<MapBeacon> = ArrayList()

    override fun getItemViewType(position: Int) = R.layout.view_beacons_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    override fun getItemCount(): Int = beacons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(beacons[position], itemClick)

    fun update(beacons: List<MapBeacon>) {
        this.beacons = beacons
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(beacon: MapBeacon, itemClick: (beacon: MapBeacon) -> Unit) {

            with(view) {
                beaconName.text = beacon.name
            }

            view.setOnClickListener { itemClick(beacon) }


        }


    }
}