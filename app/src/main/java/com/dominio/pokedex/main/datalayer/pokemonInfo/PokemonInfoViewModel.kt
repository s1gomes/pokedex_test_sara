package com.dominio.pokedex.main.datalayer.pokemonInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominio.pokedex.main.model.PokemonDetail
import com.dominio.pokedex.main.repositories.PokemonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val pokemonsRepository: PokemonsRepository
): ViewModel() {
    var _pokemonInfoUIState by mutableStateOf(PokemonInfoUIState())
        private set

    fun onEvent(event: PokemonInfoUIEvents) {
        when (event) {
            is PokemonInfoUIEvents.LoadPokemon -> {
                viewModelScope.launch {
                    _pokemonInfoUIState = _pokemonInfoUIState.copy(isLoading = true)

                    val response: Response<PokemonDetail>? = pokemonsRepository.getPokemonInfo(
                        event.pokemonName
                    )

                    if (response?.isSuccessful == true) {
                        _pokemonInfoUIState = _pokemonInfoUIState.copy(isLoading = false, pokemon = response.body())
                    } else {
                        val responseStatus = if (response?.code().toString().contains("404")) {
                            "Error 404: Page not found."
                        } else if (response?.code().toString().contains("505")) {
                            "Error 505: Internal Server Error"
                        }  else if (response?.code().toString().contains("204")) {
                            "Error 204: No Content"
                        } else {
                            "Error: Could not reach server."
                        }

                        _pokemonInfoUIState = _pokemonInfoUIState.copy(isLoading = false,
                            responseStatus = responseStatus
                        )
                    }
                }
            }
        }
    }
}
