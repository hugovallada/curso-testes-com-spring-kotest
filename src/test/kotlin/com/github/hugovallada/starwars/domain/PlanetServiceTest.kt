package com.github.hugovallada.starwars.domain

import com.github.hugovallada.starwars.common.PlanetConstants.Companion.invalidPlanet
import com.github.hugovallada.starwars.common.PlanetConstants.Companion.planet
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull


@ExtendWith(MockKExtension::class)
class PlanetServiceTest {

    @InjectMockKs
    lateinit var planetService: PlanetService

    @MockK
    lateinit var planetRepository: PlanetRepository

    @BeforeEach
    fun init() {
        clearAllMocks()
    }


    @Test
    fun `should create and return a planet when receiving validate data`() {
        every { planetRepository.save(any()) } returns planet

        // Sut = System under test
        val sut = planetService.create(planet)

        assertThat(sut).isEqualTo(planet)
    }

    @Test
    fun `should throw an exception when invalid data is send`() {
        every { planetRepository.save(any()) } throws IllegalStateException("")
        assertThrows<IllegalStateException> {
           planetService.create(invalidPlanet)
        }
    }

    @Test
    fun `should return a null value when the planet is not found in the database`() {
        every { planetRepository.findByIdOrNull(any()) } returns null

        val sut = planetService.getById(1)

        assertThat(sut == null)
        verifySequence { planetRepository.findByIdOrNull(any()) }
    }

    @Test
    fun `should return a planet when the planet is found in the database`(){
        every { planetRepository.findByIdOrNull(any()) } returns planet

        val sut = planetService.getById(1)

        assertThat(sut).isEqualTo(planet)
        verifySequence { planetRepository.findByIdOrNull(any()) }
    }

    @Test
    fun `should return a planet when there's a planet with the given name in the database`() {
        every { planetRepository.findByName(any()) } returns planet

        val sut = planetService.getByName("KALIGA")

        assertThat(sut).isEqualTo(planet)
        verifySequence { planetRepository.findByName(any()) }
    }

    @Test
    fun `should return null when there's no planet with the given name`(){
        every { planetRepository.findByName(any()) } returns null

        val sut = planetService.getByName("KALIGA")

        assertThat(sut).isNull()
        verifySequence { planetRepository.findByName(any()) }
    }

    @Test
    fun `should return a list of planets with the given filters`() {
        every { planetRepository.findByClimateAndTerrain(any(), any()) } returns listOf(planet)

        val sut = planetService.getByFilters("Hot", "Arid")

        assertThat(sut).isEqualTo(listOf(planet))
        assertThat(sut.size).isEqualTo(1)

        verifySequence { planetRepository.findByClimateAndTerrain(any(), any()) }
    }

    @Test
    fun `should delete all planets from the database`() {
        every { planetRepository.deleteAll() } just runs

        assertDoesNotThrow {
            planetService.deleteAll()
        }

        verifySequence { planetRepository.deleteAll() }


    }
}

@ExtendWith(MockKExtension::class)
class PlanetWithKotest : StringSpec({

    val planetRepository = mockk<PlanetRepository>()
    val planetService = PlanetService(planetRepository)

    beforeTest {
        clearAllMocks()
    }


    "should create and return a planet when receiving validate data" {
        every { planetRepository.save(any()) } returns planet

        val sut = planetService.create(planet)

        sut shouldBe planet

        verifySequence { planetRepository.save(any()) }
    }

    "should throw an exception when the given data is invalid" {
        every { planetRepository.save(any()) } throws RuntimeException()
        assertThrows<RuntimeException> {
            planetService.create(invalidPlanet)
        }
        verifySequence { planetRepository.save(any()) }
    }

    "should return a null value when a planet is not found using it's id" {
        every { planetRepository.findByIdOrNull(any()) } returns null
        val sut = planetService.getById(1)

        sut shouldBe null
        verifySequence { planetRepository.findByIdOrNull(any()) }
    }

    "should return a planet when it's found using it's id" {
        every { planetRepository.findByIdOrNull(any()) } returns planet
        val sut = planetService.getById(1)

        sut shouldBe planet
        verifySequence { planetRepository.findByIdOrNull(any()) }
    }

    "should return a planet when there's a planet with the given name in the database" {
        every { planetRepository.findByName(any()) } returns planet
        val sut = planetService.getByName("AKSHOA")

        sut shouldBe planet
        verifySequence { planetRepository.findByName(any()) }
    }

    "should return a null value when there's no planet with the given name in the database" {
        every { planetRepository.findByName(any()) } returns null
        val sut = planetService.getByName("AKSHOA")

        sut.shouldBeNull()

        verifySequence { planetRepository.findByName(any()) }
    }

    "should return a list of planets when search for planets in the database using filters" {
        every { planetRepository.findByClimateAndTerrain(any(), any()) } returns listOf(planet)
        val sut = planetService.getByFilters("Warm", "Arid")

        sut shouldBe listOf(planet)
        sut[0] shouldBeIn listOf(planet)
        sut.size shouldBe 1

        verifySequence { planetRepository.findByClimateAndTerrain(any(), any()) }
    }

    "should delete all planets from the database" {
        every { planetRepository.deleteAll() } just runs

        assertDoesNotThrow {
            planetService.deleteAll()
        }

        verifySequence { planetRepository.deleteAll() }
    }
})