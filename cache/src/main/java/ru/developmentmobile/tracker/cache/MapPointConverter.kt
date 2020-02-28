package ru.developmentmobile.tracker.cache

import androidx.room.TypeConverter
import kotlinx.serialization.json.JSON
import ru.developmentmobile.tracker.cache.model.MapPointListSerializable
import ru.developmentmobile.tracker.cache.model.MapPointSerializable
import java.util.stream.Collectors

object MapPointConverter {


    @TypeConverter
    @JvmStatic
    fun fromMapPointListSerializable(locations: MapPointListSerializable?): String? =
        locations?.let { JSON.stringify(MapPointListSerializable.serializer(), it) }

    @TypeConverter
    @JvmStatic
    fun toMapPointListSerializable(s: String?): MapPointListSerializable? =
        s?.let { JSON.parse(MapPointListSerializable.serializer(), it) }


    @TypeConverter
    @JvmStatic
    fun fromMapPointSerializable(s: MapPointSerializable?): String? =
        s?.let { JSON.stringify(MapPointSerializable.serializer(), it) }

    @TypeConverter
    @JvmStatic
    fun toMapPointSerializable(s: String?): MapPointSerializable? =
        s?.let { JSON.parse(MapPointSerializable.serializer(), it) }




}