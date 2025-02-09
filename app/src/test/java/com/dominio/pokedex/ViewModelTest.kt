package com.dominio.pokedex


import com.dominio.pokedex.main.datalayer.pokemon.PokemonsUIEvents
import com.dominio.pokedex.main.datalayer.pokemon.PokemonsViewModel
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoUIEvents
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoViewModel
import com.dominio.pokedex.main.model.Ability
import com.dominio.pokedex.main.model.AbilityItem
import com.dominio.pokedex.main.model.ArtItem
import com.dominio.pokedex.main.model.Dream_world
import com.dominio.pokedex.main.model.Home
import com.dominio.pokedex.main.model.Move
import com.dominio.pokedex.main.model.MoveItem
import com.dominio.pokedex.main.model.OficialArtWork
import com.dominio.pokedex.main.model.Other
import com.dominio.pokedex.main.model.Pokemon
import com.dominio.pokedex.main.model.PokemonDetail
import com.dominio.pokedex.main.model.PokemonsModel
import com.dominio.pokedex.main.model.Type
import com.dominio.pokedex.main.model.TypeItem
import com.dominio.pokedex.main.repositories.PokemonsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonsViewModelTest {

    private lateinit var viewModel: PokemonsViewModel
    private val mockRepository: PokemonsRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    suspend fun awaitThem() {
        return delay(2000L)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PokemonsViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadMorePokemons should update state with new pokemons`() = runTest {

        val mockPokemons = listOf(
            Pokemon("Pikachu", "https://pokeapi.co/api/v2/pokemon/25/"),
            Pokemon("Bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")
        )

        val mockPokemonsModel = PokemonsModel(
           results = mockPokemons
        )
        val mockResponse: Response<PokemonsModel> = Response.success(mockPokemonsModel)

        coEvery { mockRepository.getAllPokemons(any(), any()) } returns mockResponse

        viewModel._pokemonsUIState.run {
            awaitThem()
            val initialState = viewModel._pokemonsUIState
            assertTrue(initialState.pokemons.isEmpty())
            assertFalse(initialState.isLoading)
            assertFalse(initialState.error)

            viewModel.onEvent(PokemonsUIEvents.LoadMorePokemons)

            awaitThem()
            val loadingState = viewModel._pokemonsUIState
            assertTrue(loadingState.isLoading)

            awaitThem()
            val successState = viewModel._pokemonsUIState
            assertEquals(mockPokemons, successState.pokemons)
            assertFalse(successState.isLoading)
            assertFalse(successState.error)
        }
    }

    @Test
    fun `loadMorePokemons should handle error state`() = runTest {
        val errorMessage = "Network error"
        coEvery { mockRepository.getAllPokemons(any(), any()) } throws RuntimeException(errorMessage)

        viewModel._pokemonsUIState.run {
            awaitThem()
            val initialState = viewModel._pokemonsUIState
            assertTrue(initialState.pokemons.isEmpty())
            assertFalse(initialState.isLoading)
            assertFalse(initialState.error)

            viewModel.onEvent(PokemonsUIEvents.LoadMorePokemons)

            awaitThem()
            val loadingState = viewModel._pokemonsUIState
            assertTrue(loadingState.isLoading)

            awaitThem()
            val errorState = viewModel._pokemonsUIState
            assertTrue(errorState.error)
            assertEquals(errorMessage, errorState.responseStatus)
            assertFalse(errorState.isLoading)
        }
    }
}





class PokemonInfoViewModelTest {

    private lateinit var viewModel: PokemonInfoViewModel
    private val mockRepository: PokemonsRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    suspend fun awaitThem() {
        return delay(2000L)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PokemonInfoViewModel(mockRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadPokemon should update state with pokemon details on successful response`() = runTest {

        val pokemonName = "pikachu"
        val fakePokemon: PokemonDetail? = PokemonDetail(name = "pikachu",
            id = "1",
            base_experience = "2",
            abilities = listOf(AbilityItem(
                Ability(name = "")
            )),
            moves = listOf(MoveItem(
                move = Move(
                     name = ""
                )
            )),
            types = listOf(TypeItem(
                slot = "",
                Type(name = "")
            )),
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

        val mockResponse: Response<PokemonDetail> = Response.success(fakePokemon)

        coEvery { mockRepository.getPokemonInfo(pokemonName) } returns mockResponse

        viewModel._pokemonInfoUIState.run {
            awaitThem()
            val initialState = viewModel._pokemonInfoUIState
            assertNull(initialState.pokemon)
            assertFalse(initialState.isLoading)
            assertNull(initialState.responseStatus)

            viewModel.onEvent(PokemonInfoUIEvents.LoadPokemon(pokemonName))

            awaitThem()
            val loadingState = viewModel._pokemonInfoUIState
            assertTrue(loadingState.isLoading)

            awaitThem()
            val successState = viewModel._pokemonInfoUIState
            assertEquals(fakePokemon, successState.pokemon)
            assertFalse(successState.isLoading)
            assertNull(successState.responseStatus)
        }
    }

    @Test
    fun `loadPokemon should handle 404 error`() = runTest {

        val pokemonName = "unknown"
        val mockResponse: Response<PokemonDetail> = Response.error(404, mockk(relaxed = true))

        coEvery { mockRepository.getPokemonInfo(pokemonName) } returns mockResponse


        viewModel._pokemonInfoUIState.run {
           awaitThem()
            val initialState = viewModel._pokemonInfoUIState
            assertNull(initialState.pokemon)
            assertFalse(initialState.isLoading)
            assertNull(initialState.responseStatus)

            viewModel.onEvent(PokemonInfoUIEvents.LoadPokemon(pokemonName))

            awaitThem()
            val loadingState = viewModel._pokemonInfoUIState
            assertTrue(loadingState.isLoading)

            awaitThem()
            val errorState = viewModel._pokemonInfoUIState
            assertNull(errorState.pokemon)
            assertFalse(errorState.isLoading)
            assertEquals("Error 404: Page not found.", errorState.responseStatus)
        }
    }

    @Test
    fun `loadPokemon should handle 505 error`() = runTest {

        val pokemonName = "server-error"
        val mockResponse: Response<PokemonDetail> = Response.error(505, mockk(relaxed = true))

        coEvery { mockRepository.getPokemonInfo(pokemonName) } returns mockResponse


        viewModel._pokemonInfoUIState.run {
            awaitThem()
            val initialState = viewModel._pokemonInfoUIState
            assertNull(initialState.pokemon)
            assertFalse(initialState.isLoading)
            assertNull(initialState.responseStatus)


            viewModel.onEvent(PokemonInfoUIEvents.LoadPokemon(pokemonName))

            awaitThem()
            val loadingState = viewModel._pokemonInfoUIState
            assertTrue(loadingState.isLoading)

            awaitThem()
            val errorState = viewModel._pokemonInfoUIState
            assertNull(errorState.pokemon)
            assertFalse(errorState.isLoading)
            assertEquals("Error 505: Internal Server Error", errorState.responseStatus)
        }
    }

    @Test
    fun `loadPokemon should handle generic error`() = runTest {

        val pokemonName = "generic-error"
        val mockResponse: Response<PokemonDetail> = Response.error(500, mockk(relaxed = true))

        coEvery { mockRepository.getPokemonInfo(pokemonName) } returns mockResponse


        viewModel._pokemonInfoUIState.run {
            awaitThem()
            val initialState = viewModel._pokemonInfoUIState
            assertNull(initialState.pokemon)
            assertFalse(initialState.isLoading)
            assertNull(initialState.responseStatus)

            viewModel.onEvent(PokemonInfoUIEvents.LoadPokemon(pokemonName))

            awaitThem()
            val loadingState = viewModel._pokemonInfoUIState
            assertTrue(loadingState.isLoading)

            awaitThem()
            val errorState = viewModel._pokemonInfoUIState
            assertNull(errorState.pokemon)
            assertFalse(errorState.isLoading)
            assertEquals("Error: Could not reach server.", errorState.responseStatus)
        }
    }
}

