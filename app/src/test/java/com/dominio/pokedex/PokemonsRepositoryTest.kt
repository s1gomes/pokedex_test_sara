package com.dominio.pokedex

import com.dominio.pokedex.main.datasources.PokemonDataSource
import com.dominio.pokedex.main.model.ArtItem
import com.dominio.pokedex.main.model.Dream_world
import com.dominio.pokedex.main.model.Home
import com.dominio.pokedex.main.model.OficialArtWork
import com.dominio.pokedex.main.model.Other
import com.dominio.pokedex.main.model.PokemonDetail
import com.dominio.pokedex.main.model.PokemonsModel
import com.dominio.pokedex.main.repositories.PokemonsRepository
import org.junit.Test

import org.junit.Assert.*

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import retrofit2.Response

class PokemonsRepositoryTest {

    private lateinit var repository: PokemonsRepository
    private val mockDataSource: PokemonDataSource = mockk()

    @Before
    fun setUp() {
        repository = PokemonsRepository(mockDataSource)
    }

    @Test
    fun `getAllPokemons should return success response`() = runBlocking {
        val fakeResponse = Response.success(PokemonsModel(listOf()))
        coEvery { mockDataSource.getAllPokemons(0, 20) } returns fakeResponse

        val response = repository.getAllPokemons(0, 20)

        assertNotNull(response)
        assertTrue(response!!.isSuccessful)
        assertEquals(fakeResponse.body(), response.body())
    }

    @Test
    fun `getAllPokemons should return null on exception`() = runBlocking {
        coEvery { mockDataSource.getAllPokemons(any(), any()) } throws RuntimeException("API error")

        val response = repository.getAllPokemons(0, 20)

        assertNull(response)
    }

    @Test
    fun `getPokemonInfo should return success response`() = runBlocking {
        val fakePokemon = PokemonDetail(name = "pikachu",
            id = "1",
            base_experience = "2",
            abilities = listOf(),
            moves = listOf(),
            types = listOf(),
            sprites = Other(
                other = ArtItem(
                    home = Home(
                        front_default = ""
                    ),
                    dream_world = Dream_world(
                        front_default = ""
                    ),
                    officialArtwork = OficialArtWork(
                        front_default = "",
                        front_shiny = ""
                    )
                )
            )
        )
        val fakeResponse = Response.success(fakePokemon)
        coEvery { mockDataSource.getPokemonInfo("pikachu") } returns fakeResponse

        val response = repository.getPokemonInfo("pikachu")

        assertNotNull(response)
        assertTrue(response!!.isSuccessful)
        assertEquals(fakePokemon, response.body())
    }

    @Test
    fun `getPokemonInfo should return null on exception`() = runBlocking {
        coEvery { mockDataSource.getPokemonInfo(any()) } throws RuntimeException("Network error")

        val response = repository.getPokemonInfo("pikachu")

        assertNull(response)
    }
}