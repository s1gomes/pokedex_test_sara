package com.dominio.pokedex.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dominio.pokedex.main.components.AppBarPokemonDetail
import com.dominio.pokedex.main.components.PokemonDescription
import com.dominio.pokedex.main.components.PokemonImage
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoUIEvents
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoViewModel
import com.dominio.pokedex.main.ui.theme.CustomLightColors

@Composable
fun PokemonDetailScreen(
    pokemonId: String,
    navController: NavController,
    pokemonInfoViewModel: PokemonInfoViewModel
) {
    val pokemonDetail = pokemonInfoViewModel._pokemonInfoUIState
    val scrollableState = rememberScrollState()

    LaunchedEffect(Unit) {
        pokemonInfoViewModel.onEvent(PokemonInfoUIEvents.LoadPokemon(pokemonId))
    }

    Column(
      modifier = Modifier.fillMaxSize()
    ) {
        AppBarPokemonDetail(
            navController,
            pokemonDetail
        )
        Column(
            modifier = Modifier
                .background(CustomLightColors.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PokemonImage(
                paddingValues = PaddingValues(10.dp),
                pokemonDetail = pokemonDetail,
                modifier = Modifier
                    .weight(0.25F)
            )
            PokemonDescription(
                pokemonDetail = pokemonDetail,
                scrollState = scrollableState,
                modifier = Modifier
                    .weight(0.75F)
            )
        }
    }
}
