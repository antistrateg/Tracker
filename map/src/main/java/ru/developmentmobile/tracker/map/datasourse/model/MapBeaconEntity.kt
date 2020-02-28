package ru.developmentmobile.tracker.map.datasourse.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapPoint

data class MapBeaconsEntityWrapper(
    @SerializedName(BEACONS) @Expose val beacons: List<MapBeaconEntity>
) {
    companion object {
        private const val BEACONS = "beacons"
    }
}

data class MapBeaconEntityWrapper(
    @SerializedName(BEACON) @Expose val beacon: MapBeaconEntity
) {
    companion object {
        private const val BEACON = "beacon"
    }
}

data class MapBeaconEntity(
    @SerializedName(ID) @Expose val id: Int,
    @SerializedName(NAME) @Expose val name: String,
    @SerializedName(LATITUDE) @Expose val latitude: Double,
    @SerializedName(LONGITUDE) @Expose val longitude: Double,
    @SerializedName(TIME) @Expose val time: Long
) {
    companion object {
        private const val ID = "id"
        private const val NAME = "name"
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
        private const val TIME = "time"
    }
}

fun MapBeaconEntity.mapToDomain(): MapBeacon = MapBeacon(
    id,
    name,
    MapPoint(latitude,longitude),
    time
)

fun List<MapBeaconEntity>.mapToDomain(): List<MapBeacon> = map { it.mapToDomain() }