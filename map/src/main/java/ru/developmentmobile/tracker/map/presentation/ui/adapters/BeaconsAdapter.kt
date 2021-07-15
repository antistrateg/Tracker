package ru.developmentmobile.tracker.map.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_beacons_item.view.*
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.domain.model.MapBeacon

class BeaconsAdapter(
    private val itemClick: (beacon: MapBeacon, isSelected: Boolean) -> Unit
) : RecyclerView.Adapter<BeaconsAdapter.ViewHolder>() {

    private var beacons: List<MapBeacon> = ArrayList()
    private var selected: MapBeacon? = null

    override fun getItemViewType(position: Int) = R.layout.view_beacons_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    override fun getItemCount(): Int = beacons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(beacons[position], selected, itemClick)

    fun update(beacons: List<MapBeacon>, selected: MapBeacon?) {
        this.beacons = beacons
        this.selected = selected
        notifyDataSetChanged()
    }

    fun update(selected: MapBeacon?) {
        this.selected = selected
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            beacon: MapBeacon,
            selected: MapBeacon?,
            itemClick: (beacon: MapBeacon, isSelected: Boolean) -> Unit
        ) {

            val isSelected = selected != null && selected.id == beacon.id

            with(view) {
                beaconName.text = beacon.name
                if (isSelected) beaconCheckButton.visibility = View.VISIBLE
                else beaconCheckButton.visibility = View.GONE
            }

            view.setOnClickListener { itemClick(beacon, isSelected) }
        }
    }
}