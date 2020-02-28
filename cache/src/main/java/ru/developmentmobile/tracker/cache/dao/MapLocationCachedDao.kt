package ru.developmentmobile.tracker.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.developmentmobile.tracker.cache.model.MapLocationCached

@Dao
interface MapLocationCachedDao {

//    @Query("SELECT * FROM locations where `id`=:key")
//    fun select(key: String): MapLocationCached
//
//    @Query("SELECT * FROM locations where id=:id")
//    fun select(id: Int): MapLocationCached

    @Query("SELECT * FROM locations ORDER BY id DESC")
    fun selectAll(): List<MapLocationCached>
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: MapLocationCached)

    @Query("DELETE FROM locations where `id`=:id")
    fun deleteById(id: Int)

    @Query("DELETE FROM locations")
    fun deleteAll()
}