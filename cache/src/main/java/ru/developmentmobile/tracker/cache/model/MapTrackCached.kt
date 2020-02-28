package ru.developmentmobile.tracker.cache.model

import androidx.room.*
import ru.developmentmobile.tracker.cache.MapPointConverter
import ru.developmentmobile.tracker.map.domain.model.MapTrack

@Entity(tableName = "tracks")
data class MapTrackCached (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val locations: MapPointListSerializable
)


//fun MapTrackCached.mapFromCache(): MapTrack = MapTrack(
//    id,
//    name,
////    locations.mapFromCache()
//
//)

//fun List<MapTrackCached>.mapFromCache(): List<MapTrack> = map { it.mapFromCache() }




