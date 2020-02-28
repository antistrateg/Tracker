package ru.developmentmobile.tracker.cache.dao

import androidx.room.*
import ru.developmentmobile.tracker.cache.model.MapLocationCached
import ru.developmentmobile.tracker.cache.model.MapTrackCached

@Dao
interface MapTrackCachedDao {

//    @Query("SELECT * FROM locations where `id`=:key")
//    fun select(key: String): MapLocationCached
//
//    @Query("SELECT * FROM locations where id=:id")
//    fun select(id: Int): MapLocationCached

//    @Query("SELECT * FROM tracks ORDER BY id DESC")
//    fun selectAll(): List<MapTrackCached>
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: MapTrackCached)
//
//    @Query("DELETE FROM tracks where `id`=:id")
//    fun deleteById(id: Int)
//
    @Query("DELETE FROM tracks")
    fun deleteAll()
}