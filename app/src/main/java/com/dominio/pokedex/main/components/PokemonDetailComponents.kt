package com.dominio.pokedex.main.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominio.pokedex.R
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoUIState
import com.dominio.pokedex.main.ui.theme.CustomLightColors



@Composable
fun PokemonImage(
    paddingValues: PaddingValues,
    pokemonDetail: PokemonInfoUIState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(CustomLightColors.background),
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonDescription(
    paddingValues: PaddingValues,
    pokemonDetail: PokemonInfoUIState,
    modifier: Modifier = Modifier,
    scrollState: ScrollState
) {
    val movesList = pokemonDetail.pokemon?.moves?.map { it.move.name } ?: emptyList()
    val abilitiesList = pokemonDetail.pokemon?.abilities?.map { it.ability.name } ?: emptyList()
    val typesList = pokemonDetail.pokemon?.types?.map { it.type.name } ?: emptyList()

    val brush = Brush.verticalGradient(
        listOf(CustomLightColors.background, CustomLightColors.onPrimary)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(brush),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.elevatedCardElevation(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardColors(
                    contentColor = CustomLightColors.onBackground,
                    disabledContentColor = CustomLightColors.surface,
                    disabledContainerColor = CustomLightColors.surface,
                    containerColor = CustomLightColors.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            color = CustomLightColors.surface
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Type",
                        style = MaterialTheme.typography.headlineSmall,
                        color = CustomLightColors.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        typesList.forEach { type ->
                            AssistChip(
                                onClick = {},
                                label = {
                                    Text(type.replaceFirstChar { it.uppercaseChar() },
                                        color = CustomLightColors.onBackground)
                                    },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = CustomLightColors.error
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.elevatedCardElevation(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardColors(
                    contentColor = CustomLightColors.onBackground,
                    disabledContentColor = CustomLightColors.surface,
                    disabledContainerColor = CustomLightColors.surface,
                    containerColor = CustomLightColors.surface
                )

            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            color = CustomLightColors.surface
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Abilities",
                        style = MaterialTheme.typography.headlineSmall,
                        color = CustomLightColors.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        abilitiesList.forEach { ability ->
                            AssistChip(
                                onClick = {},
                                label = { Text(ability.replaceFirstChar { it.uppercaseChar() }) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = CustomLightColors.tertiary
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Moves",
                style = MaterialTheme.typography.headlineSmall,
                color = CustomLightColors.onSurface,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.leftarrow),
                    contentDescription = "back",
                    modifier = Modifier.size(18.dp),
                    tint = CustomLightColors.surface
                )
                Icon(
                    painter = painterResource(id = R.drawable.rightarrow),
                    contentDescription = "back",
                    modifier = Modifier.size(18.dp),
                    tint = CustomLightColors.surface
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .horizontalScroll(
                        scrollState
                    )
            ) {

               for (move in movesList) {
                    AssistChip(
                        onClick = {},
                        label = {
                            Text(move.replaceFirstChar { it.uppercaseChar() })
                            },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = CustomLightColors.primary.copy(alpha = 0.8f)
                        ),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
//
//@Composable
//fun PokemonDescription(
//    paddingValues: PaddingValues,
//    pokemonDetail: PokemonInfoUIState,
//    modifier: Modifier = Modifier,
//    scrollableState: ScrollState
//){
//
//    val movesList = listOf(pokemonDetail.pokemon?.moves?.map { moves -> moves.move.name})
//    val brush = Brush.verticalGradient(listOf( CustomLightColors.background, CustomLightColors.onPrimary))
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(paddingValues)
//            .background(brush = brush),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(5.dp)
//                .background(Color.Transparent),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Type: ${pokemonDetail.pokemon?.types?.map { type -> type.type.name }.toString().replace("[", "").replace("]", "")} Slot: ${pokemonDetail.pokemon?.types?.map { type -> type.slot }.toString().replace("[", "").replace("]", "")}",
//                style = MaterialTheme.typography.headlineMedium,
//                color = CustomLightColors.onBackground,
//                overflow = TextOverflow.Clip
//            )
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp)
//                .background(Color.Transparent),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Abilities",
//                style = MaterialTheme.typography.headlineMedium,
//                color = CustomLightColors.onBackground,
//                overflow = TextOverflow.Clip
//            )
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.Transparent),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "${pokemonDetail.pokemon?.abilities?.map { abilities -> abilities.ability.name }.toString().replace("[", "").replace("]", "")} ",
//                style = MaterialTheme.typography.headlineSmall,
//                color = CustomLightColors.onBackground,
//                overflow = TextOverflow.Clip
//            )
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp)
//                .background(Color.Transparent),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Moves",
//                style = MaterialTheme.typography.headlineMedium,
//                color = CustomLightColors.onBackground,
//                overflow = TextOverflow.Clip
//            )
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp)
//                .background(Color.Transparent)
//                .verticalScroll(scrollableState),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = movesList.toString().replace("[", "").replace("]", ""),
//                style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
//                color = CustomLightColors.onBackground,
//                textAlign = TextAlign.Justify,
//                overflow = TextOverflow.Clip
//            )
//        }
//    }
//}
//


