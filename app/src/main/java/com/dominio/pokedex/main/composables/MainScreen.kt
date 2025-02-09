package com.dominio.pokedex.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dominio.pokedex.R
import com.dominio.pokedex.main.components.AppBarPokemonTitle
import com.dominio.pokedex.main.components.PokemonItem
import com.dominio.pokedex.main.components.PokemonsList
import com.dominio.pokedex.main.datalayer.pokemon.PokemonsUIEvents
import com.dominio.pokedex.main.datalayer.pokemon.PokemonsUIState
import com.dominio.pokedex.main.datalayer.pokemon.PokemonsViewModel
import com.dominio.pokedex.main.model.Pokemon
import com.dominio.pokedex.main.ui.theme.CustomLightColors
import java.util.Locale


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

