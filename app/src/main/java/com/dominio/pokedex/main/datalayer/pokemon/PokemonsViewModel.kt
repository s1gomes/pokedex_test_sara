package com.dominio.pokedex.main.datalayer.pokemon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominio.pokedex.main.pagination.Paginator
import com.dominio.pokedex.main.repositories.PokemonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonsViewModel @Inject constructor(
    private val pokemonsRepository: PokemonsRepository
): ViewModel() {
    var _pokemonsUIState by mutableStateOf(PokemonsUIState())
        private set

    private val paginator = Paginator(
        onLoadUpdated = {
            _pokemonsUIState = _pokemonsUIState.copy(isLoading = it)
        },
        onRequest = { offset, limit ->
            pokemonsRepository.getAllPokemons(offset = offset, limit = limit)
        },
        onError = {
            val responseStatus = if (it.toString().contains("404")) {
                "Error 404: Page not found."
            } else if (it.toString().contains("505")) {
                "Error 505: Internal Server Error"
            }  else if (it.toString().contains("204")) {
                "Error 204: No Content"
            } else {
                "Error: $it Could not reach server."
            }
            _pokemonsUIState = _pokemonsUIState.copy(isLoading = false, error = true, responseStatus = responseStatus)
        },
        onSuccess = { items ->
            _pokemonsUIState = _pokemonsUIState.copy(
                pokemons = _pokemonsUIState.pokemons + items.results,
                isLoading = false,
                error = false,
                endReached = items.results.isEmpty())
        }
    )

    init {
        onEvent(PokemonsUIEvents.LoadMorePokemons)
    }

    fun onEvent(event: PokemonsUIEvents) {
        when (event) {
            is PokemonsUIEvents.LoadMorePokemons -> {
                viewModelScope.launch {
                    paginator.loadNextItems()
                }
            }
        }
    }
}