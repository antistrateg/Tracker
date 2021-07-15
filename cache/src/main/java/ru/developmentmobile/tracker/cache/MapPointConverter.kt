package ru.developmentmobile.tracker.cache

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import ru.developmentmobile.tracker.cache.model.MapPointListSerializable
import ru.developmentmobile.tracker.cache.model.MapPointSerializable

object MapPointConverter {

    @TypeConverter
    @JvmStatic
    fun fromMapPointListSerializable(locations: MapPointListSerializable?): String? =
        locations?.let { Json.encodeToString(MapPointListSerializable.serializer(), it) }

    @TypeConverter
    @JvmStatic
    fun toMapPointListSerializable(s: String?): MapPointListSerializable? =
        s?.let { Json.decodeFromString(MapPointListSerializable.serializer(), it) }


    @TypeConverter
    @JvmStatic
    fun fromMapPointSerializable(s: MapPointSerializable?): String? =
        s?.let { Json.encodeToString(MapPointSerializable.serializer(), it) }

    @TypeConverter
    @JvmStatic
    fun toMapPointSerializable(s: String?): MapPointSerializable? =
        s?.let { Json.decodeFromString(MapPointSerializable.serializer(), it) }

}