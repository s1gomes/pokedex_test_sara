package com.dominio.pokedex.main.network

import com.dominio.pokedex.main.model.PokemonDetail
import com.dominio.pokedex.main.model.PokemonsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getAllPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonsModel>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Response<PokemonDetail>

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}