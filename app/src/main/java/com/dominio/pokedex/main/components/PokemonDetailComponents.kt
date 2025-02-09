package com.dominio.pokedex.main.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoUIState
import com.dominio.pokedex.main.ui.theme.CustomLightColors



@Composable
fun PokemonImage(
    paddingValues: PaddingValues,
    pokemonDetail: PokemonInfoUIState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
            ImageItem(
                collumnmodifier = Modifier.weight(0.3F),
                paddingValues = paddingValues,
                imagemodifier = Modifier.height(120.dp),
                pokemonDetail = pokemonDetail.pokemon?.sprites?.other?.officialArtwork?.front_shiny.toString(),
                label = "Shiny"
            )
            ImageItem(
                collumnmodifier = Modifier.weight(0.4F),
                paddingValues = paddingValues,
                imagemodifier = Modifier.height(190.dp),
                pokemonDetail = pokemonDetail.pokemon?.sprites?.other?.officialArtwork?.front_default.toString(),
                label = ""
            )
            ImageItem(
                collumnmodifier = Modifier.weight(0.3F),
                paddingValues = paddingValues,
                imagemodifier = Modifier.height(120.dp),
                pokemonDetail = pokemonDetail.pokemon?.sprites?.other?.home?.front_default.toString(),
                label = "3D"
            )
        }
}

@Composable
fun PokemonDescription(
    paddingValues: PaddingValues,
    pokemonDetail: PokemonInfoUIState,
    modifier: Modifier = Modifier,
    scrollableState: ScrollState
){

    val movesList = listOf(pokemonDetail.pokemon?.moves?.map { moves -> moves.move.name})
    val brush = Brush.verticalGradient(listOf( CustomLightColors.background, CustomLightColors.primary))
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(brush = brush),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Type: ${pokemonDetail.pokemon?.types?.map { type -> type.type.name }.toString().replace("[", "").replace("]", "")} Slot: ${pokemonDetail.pokemon?.types?.map { type -> type.slot }.toString().replace("[", "").replace("]", "")}",
                style = MaterialTheme.typography.headlineMedium,
                color = CustomLightColors.onBackground,
                overflow = TextOverflow.Clip
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Abilities",
                style = MaterialTheme.typography.headlineMedium,
                color = CustomLightColors.onBackground,
                overflow = TextOverflow.Clip
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${pokemonDetail.pokemon?.abilities?.map { abilities -> abilities.ability.name }.toString().replace("[", "").replace("]", "")} ",
                style = MaterialTheme.typography.headlineSmall,
                color = CustomLightColors.onBackground,
                overflow = TextOverflow.Clip
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Moves",
                style = MaterialTheme.typography.headlineMedium,
                color = CustomLightColors.onBackground,
                overflow = TextOverflow.Clip
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.Transparent)
                .verticalScroll(scrollableState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = movesList.toString().replace("[", "").replace("]", ""),
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
                color = CustomLightColors.onBackground,
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Clip
            )
        }
    }
}



