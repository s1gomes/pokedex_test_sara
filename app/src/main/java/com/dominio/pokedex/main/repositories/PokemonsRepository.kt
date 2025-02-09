package com.dominio.pokedex.main.repositories

import android.util.Log
import com.dominio.pokedex.main.datasources.PokemonDataSource
import com.dominio.pokedex.main.model.PokemonDetail
import com.dominio.pokedex.main.model.PokemonsModel
import retrofit2.Response
import javax.inject.Inject

class PokemonsRepository @Inject constructor(
    val pokemonsDataSource: PokemonDataSource
){

    suspend fun getAllPokemons(offset: Int, limit: Int): Response<PokemonsModel>? {
        var response: Response<PokemonsModel>? = null

        try {
            response = pokemonsDataSource.getAllPokemons(offset, limit)
        } catch (exception: Exception) {
//            Log.e(javaClass.name, exception.message.toString())
//            Log.e(javaClass.name, exception.cause.toString())
//            Log.e(javaClass.name, exception.stackTraceToString())
//            Log.e(javaClass.name, exception.suppressed.toString())
        }
        return response
    }

    suspend fun getPokemonInfo(
        pokemonName: String
    ): Response<PokemonDetail>? {
        var response: Response<PokemonDetail>? = null

        try {
            response = pokemonsDataSource.getPokemonInfo(pokemonName)
        } catch (exception: Exception) {
//            Log.e(javaClass.name, exception.message.toString())
//            Log.e(javaClass.name, exception.cause.toString())
//            Log.e(javaClass.name, exception.stackTraceToString())
//            Log.e(javaClass.name, exception.suppressed.toString())
        }
        return response
    }
}