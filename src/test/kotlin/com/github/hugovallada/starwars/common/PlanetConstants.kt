package com.github.hugovallada.starwars.common

import com.github.hugovallada.starwars.domain.Planet

class PlanetConstants {
    companion object{
        val planet: Planet = Planet(1,"name", "climate", "terrain")
        val invalidPlanet = Planet(0, "", "", "")
    }
}