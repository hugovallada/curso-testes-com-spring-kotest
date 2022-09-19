package com.github.hugovallada.starwars.controller

import com.github.hugovallada.starwars.domain.Planet
import com.github.hugovallada.starwars.domain.PlanetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/planets")
class PlanetController(private val planetService: PlanetService) {


    @PostMapping
    fun createPlanet(@RequestBody planet: Planet): ResponseEntity<Planet> {
        with(planetService.create(planet)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(this)
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = with(planetService.getById(id)) {
        this?.let { planet -> ResponseEntity.ok(planet) } ?: ResponseEntity.notFound()
    }

    @GetMapping("/name")
    fun getByName(@PathVariable name: String) = with(planetService.getByName(name)) {
        this?.let { planet -> ResponseEntity.ok(planet) } ?: ResponseEntity.notFound()
    }

    @GetMapping
    fun getByFilters(@RequestParam climate: String, @RequestParam terrain: String) =
        ResponseEntity.ok(planetService.getByFilters(climate, terrain))

    @DeleteMapping
    fun removeAllPlanets(): ResponseEntity<Any> {
        planetService.deleteAll()
        return ResponseEntity.noContent().build()
    }
}