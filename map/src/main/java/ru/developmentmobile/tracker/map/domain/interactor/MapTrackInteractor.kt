package ru.developmentmobile.tracker.map.domain.interactor

import ru.developmentmobile.tracker.map.domain.repository.MapRepository

class MapTrackInteractor (
    private val mapRepository: MapRepository
) {
    suspend fun getTracks() = mapRepository.getTracks()
    suspend fun updateTrack(trackId: Int) = mapRepository.updateTrack(trackId)
    suspend fun deleteTrack(trackId: Int) = mapRepository.deleteTrack(trackId)
    suspend fun deleteTracks() = mapRepository.deleteTracks()
}
