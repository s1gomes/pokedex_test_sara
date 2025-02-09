package com.dominio.pokedex.main.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.dominio.pokedex.R
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoUIEvents
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoUIState
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoViewModel
import com.dominio.pokedex.main.model.PokemonDetail
import com.dominio.pokedex.main.ui.theme.CustomLightColors
import java.util.Locale

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(
                            modifier = Modifier.weight(0.1F),
                            onClick = {navController.navigateUp() },
                            ) {
                            Icon(
                                painter = painterResource(id = R.drawable.back),
                                contentDescription = "back")
                        }
                        Row(
                            modifier = Modifier.weight(0.9F),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "${pokemonDetail.pokemon?.id} - ${pokemonDetail.pokemon?.name.toString().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}    ",
                                style = MaterialTheme.typography.headlineLarge,
                                color = CustomLightColors.secondary
                            )
                        }
                    }

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
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .weight(0.3F)
                .padding(paddingValues)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(120.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonDetail.pokemon?.sprites?.other?.officialArtwork?.front_shiny.toString())
                    .crossfade(true)
                    .build(),
                contentDescription = "pokemonImage",
                contentScale = ContentScale.Inside,
                placeholder = painterResource(id = R.drawable.placeholder_img),
                error = painterResource(id = R.drawable.error_img),
                onLoading = { loading: AsyncImagePainter.State.Loading ->
                    loading.painter
                }
            )
            Text(text = "Shiny",
                style = MaterialTheme.typography.labelLarge)
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .weight(0.4F)
                .padding(paddingValues)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(190.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonDetail.pokemon?.sprites?.other?.officialArtwork?.front_default.toString())
                    .crossfade(true)
                    .build(),
                contentDescription = "pokemonImage",
                contentScale = ContentScale.Inside,
                placeholder = painterResource(id = R.drawable.placeholder_img),
                error = painterResource(id = R.drawable.error_img),
                onLoading = { loading: AsyncImagePainter.State.Loading ->
                    loading.painter
                }
            )

        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .weight(0.3F)
                .padding(paddingValues)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(120.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonDetail.pokemon?.sprites?.other?.home?.front_default.toString())
                    .crossfade(true)
                    .build(),
                contentDescription = "pokemonImage",
                contentScale = ContentScale.Inside,
                placeholder = painterResource(id = R.drawable.placeholder_img),
                error = painterResource(id = R.drawable.error_img),
                onLoading = { loading: AsyncImagePainter.State.Loading ->
                    loading.painter
                }
            )
            Text(text = "3D",
                style = MaterialTheme.typography.labelLarge)
        }
    }
}
