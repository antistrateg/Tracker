package ru.developmentmobile.tracker.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapPoint

@Serializable
data class MapPointSerializable (
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class MapPointListSerializable(
    val list: List<MapPointSerializable>
)


fun MapPointSerializable.mapFromCache(): MapPoint = MapPoint(
    latitude,
    longitude
)

fun MapPointListSerializable.mapFromCache(): List<MapPoint> = list.map { it.mapFromCache() }

fun MapPoint.mapToCache(): MapPointSerializable = MapPointSerializable(
    latitude,
    longitude
)




