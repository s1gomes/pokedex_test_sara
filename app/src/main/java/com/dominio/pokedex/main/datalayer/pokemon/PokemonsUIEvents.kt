package com.dominio.pokedex.main.datalayer.pokemon

sealed class PokemonsUIEvents {
    object LoadMorePokemons: PokemonsUIEvents()
}