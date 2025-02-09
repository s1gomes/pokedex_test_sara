package com.dominio.pokedex.main.composables

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.dominio.pokedex.main.components.AppBarPokemonTitle
import com.dominio.pokedex.main.components.PokemonsList
import com.dominio.pokedex.main.datalayer.pokemon.PokemonsViewModel
import com.dominio.pokedex.main.ui.theme.CustomLightColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    pokemonsViewModel: PokemonsViewModel,
) {
    val listState = rememberLazyGridState()
    val pokemonsUIState = pokemonsViewModel._pokemonsUIState

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = CustomLightColors.primary,
                    titleContentColor = CustomLightColors.background,
                ),
                title = {
                   AppBarPokemonTitle()
            })
        },
    ) {
        PokemonsList(
            pokemons = pokemonsUIState.pokemons,
            listState = listState,
            paddingValues = it,
            pokemonsUIState = pokemonsUIState,
            pokemonsViewModel = pokemonsViewModel,
            onClickPokemon = { pokemonName ->
                navController.navigate("pokemon_detail/$pokemonName")
            }
        )
    }
}

