package com.dominio.pokedex.main.datalayer.pokemon

import com.dominio.pokedex.main.model.Pokemon

data class PokemonsUIState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = listOf(),
    val responseStatus: String = "",
    val error: Boolean = false,
    val endReached: Boolean = false
)