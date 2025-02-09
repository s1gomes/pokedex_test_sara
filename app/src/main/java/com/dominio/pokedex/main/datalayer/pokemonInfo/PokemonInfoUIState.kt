package com.dominio.pokedex.main.datalayer.pokemonInfo

import com.dominio.pokedex.main.model.PokemonDetail

data class PokemonInfoUIState(
    val isLoading: Boolean = false,
    val pokemon: PokemonDetail? = null,
    val responseStatus: String = "",
    val error: String? = null,
    val endReached: Boolean = false
)