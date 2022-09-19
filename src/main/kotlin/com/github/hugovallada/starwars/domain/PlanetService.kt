package com.github.hugovallada.starwars.domain

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlanetService(private val repository: PlanetRepository) {

    fun create(planet: Planet): Planet {
        return repository.save(planet)
    }

    fun getById(id: Long) = repository.findByIdOrNull(id)

    fun getByName(name: String) = repository.findByName(name)

    fun getByFilters(climate: String, terrain: String) = repository.findByClimateAndTerrain(climate, terrain)
    fun deleteAll() {
        repository.deleteAll()
    }
}