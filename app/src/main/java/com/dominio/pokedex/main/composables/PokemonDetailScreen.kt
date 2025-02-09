package com.dominio.pokedex.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dominio.pokedex.main.components.AppBarPokemonDetail
import com.dominio.pokedex.main.components.PokemonDescription
import com.dominio.pokedex.main.components.PokemonImage
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoUIEvents
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoViewModel
import com.dominio.pokedex.main.ui.theme.CustomLightColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    pokemonId: String,
    navController: NavController,
    pokemonInfoViewModel: PokemonInfoViewModel
) {
    val pokemonDetail = pokemonInfoViewModel._pokemonInfoUIState

    LaunchedEffect(Unit) {
        pokemonInfoViewModel.onEvent(PokemonInfoUIEvents.LoadPokemon(pokemonId))
    }
    val scrollableState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = CustomLightColors.primary,
                    titleContentColor = CustomLightColors.background,
                ),
                title = {
                    AppBarPokemonDetail(
                        navController,
                        pokemonDetail
                    )
                })
        },
    ) {
        Column(
            modifier = Modifier
                .background(CustomLightColors.background)
        ) {
            PokemonImage(
                paddingValues = it,
                pokemonDetail = pokemonDetail,
                modifier = Modifier
                    .weight(0.3F)
            )
            PokemonDescription(
                paddingValues = it,
                pokemonDetail = pokemonDetail,
                modifier = Modifier
                    .weight(0.7F),
                scrollableState = scrollableState
            )
        }
    }
}
