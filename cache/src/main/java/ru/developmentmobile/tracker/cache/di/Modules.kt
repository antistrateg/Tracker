package ru.developmentmobile.tracker.cache.di

import androidx.room.Room
import org.koin.dsl.module
import ru.developmentmobile.tracker.cache.CacheDatabase
import ru.developmentmobile.tracker.cache.CacheMapImpl
import ru.developmentmobile.tracker.map.datasourse.cache.MapCache

val cacheModule = module {
    single {
        Room.databaseBuilder(get(), CacheDatabase::class.java, "cache.db")
            .fallbackToDestructiveMigration().build()
    }

    single <MapCache> { CacheMapImpl() }
}