package com.github.hugovallada.starwars.domain

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlanetRepository: CrudRepository<Planet, Long> {
    fun findByName(name: String): Planet?
    fun findByClimateAndTerrain(climate: String, terrain: String): List<Planet>

}
