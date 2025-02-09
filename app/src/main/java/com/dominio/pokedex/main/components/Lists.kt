package com.dominio.pokedex.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
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
    var isFirstLoading by remember {
        mutableStateOf(true)
    }
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
            modifier = Modifier.fillMaxSize()
                .background(CustomLightColors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pokemon list is empty.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = CustomLightColors.error
                )
                IconButton(onClick = {
                    pokemonsViewModel.onEvent(PokemonsUIEvents.LoadMorePokemons)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.reload),
                        contentDescription = "reload",
                        tint = CustomLightColors.onSurface,
                        modifier = Modifier.size(30.dp))
                }
            }
        }
    } else if (pokemonsUIState.error){
        Column(
            modifier = Modifier.fillMaxSize()
                .background(CustomLightColors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${pokemonsUIState.responseStatus} \nTente novamente.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = CustomLightColors.error,
                    overflow = TextOverflow.Clip
                )
                IconButton(onClick = {
                    pokemonsViewModel.onEvent(PokemonsUIEvents.LoadMorePokemons)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.reload),
                        contentDescription = "reload",
                        tint = CustomLightColors.onSurface,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }

    else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
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
                if (isFirstLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize().background(CustomLightColors.background),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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
                } else {
                    isFirstLoading = false
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(CustomLightColors.background),
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
