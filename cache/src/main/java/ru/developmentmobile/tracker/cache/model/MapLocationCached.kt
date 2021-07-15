package ru.developmentmobile.tracker.cache.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.developmentmobile.tracker.map.domain.model.MapLocation

@Entity(tableName = "locations")
data class MapLocationCached (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int,
    @ColumnInfo
    val location: MapPointSerializable,
    @ColumnInfo
    val title: String? = null,
    @ColumnInfo
    val address: String
)


fun MapLocationCached.mapFromCache(): MapLocation = MapLocation(
    id,
    location.mapFromCache(),
    title,
    address
)

fun List<MapLocationCached>.mapFromCache(): List<MapLocation> = map { it.mapFromCache() }

fun MapLocation.mapToCache(): MapLocationCached = MapLocationCached(
    id,
    location.mapToCache(),
    title,
    address
)