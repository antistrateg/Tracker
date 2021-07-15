package ru.developmentmobile.tracker.map.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_tracks_item.view.*
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.domain.model.MapTrack

class TracksAdapter(
    private val itemClick: (track: MapTrack) -> Unit,
    private val itemDelete: (track: MapTrack) -> Unit
) : RecyclerView.Adapter<TracksAdapter.ViewHolder>() {

    private var tracks : List<MapTrack> = ArrayList()

    override fun getItemViewType(position: Int) = R.layout.view_tracks_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(tracks[position], itemClick, itemDelete)

    fun update(tracks: List<MapTrack>) {
        this.tracks = tracks
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            track: MapTrack,
            itemClick: (track: MapTrack) -> Unit,
            itemDelete: (track: MapTrack) -> Unit
        ) {

            with(view) {
                trackName.text = track.name
            }

            view.setOnClickListener { itemClick(track) }

        }


    }
}