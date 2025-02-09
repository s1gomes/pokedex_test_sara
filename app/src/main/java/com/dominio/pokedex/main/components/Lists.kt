package com.dominio.pokedex.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dominio.pokedex.R
import com.dominio.pokedex.main.datalayer.pokemon.PokemonsUIEvents
import com.dominio.pokedex.main.datalayer.pokemon.PokemonsUIState
import com.dominio.pokedex.main.datalayer.pokemon.PokemonsViewModel
import com.dominio.pokedex.main.model.Pokemon
import com.dominio.pokedex.main.ui.theme.CustomLightColors

@Composable
fun PokemonsList(
    pokemons: List<Pokemon>,
    listState: LazyGridState,
    paddingValues: PaddingValues,
    pokemonsUIState: PokemonsUIState,
    onClickPokemon: (name: String) -> Unit,
    pokemonsViewModel: PokemonsViewModel
) {
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                val totalItems = layoutInfo.totalItemsCount

                if (lastVisibleItem != null && lastVisibleItem.index >= totalItems - 1) {
                    pokemonsViewModel.onEvent(PokemonsUIEvents.LoadMorePokemons)
                }
            }
    }
    if (pokemons.isEmpty() && !pokemonsUIState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pokemon list is empty",
                style = MaterialTheme.typography.headlineMedium,
                color = CustomLightColors.error
            )
        }
    } else if (pokemonsUIState.error){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Error: ${pokemonsUIState.responseStatus}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = CustomLightColors.error
                )
                IconButton(onClick = {
                    pokemonsViewModel.onEvent(PokemonsUIEvents.LoadMorePokemons)
                }) {
                    Icon(painter = painterResource(id = R.drawable.reload), contentDescription = "reload")
                }
            }
        }
    }

    else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomLightColors.background),
            contentPadding = paddingValues
        ) {
            items(pokemons.size) { pokemon ->
                PokemonItem(
                    pokemonName = pokemons[pokemon].name,
                    onClickPokemon = {
                        onClickPokemon(pokemons[pokemon].name)
                    }
                )
            }

            item {
                if (pokemonsUIState.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }
}
