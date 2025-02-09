package com.dominio.pokedex.main.datasources

import com.dominio.pokedex.main.network.PokemonApi
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    suspend fun getAllPokemons(offset: Int, limit: Int) = pokemonApi.getAllPokemons(offset, limit)

    suspend fun getPokemonInfo(
        pokemonName: String
    ) = pokemonApi.getPokemonInfo(name = pokemonName)

}