package com.dominio.pokedex.main.datalayer.pokemonInfo

sealed class PokemonInfoUIEvents {
    data class LoadPokemon(
        val pokemonName: String
    ): PokemonInfoUIEvents()

}