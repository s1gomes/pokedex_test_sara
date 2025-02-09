package com.dominio.pokedex.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.dominio.pokedex.R
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoUIState
import com.dominio.pokedex.main.ui.theme.CustomLightColors
import java.util.Locale


@Composable
fun PokemonItem(
    pokemonName: String,
    onClickPokemon: () -> Unit
) {
    Spacer(modifier = Modifier.height(5.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClickPokemon() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomLightColors.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pokemonName.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                },
                color = CustomLightColors.onSurface,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}



@Composable
fun ImageItem(
    collumnmodifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    imagemodifier: Modifier = Modifier,
    pokemonDetail: String,
    label: String) {
    Column(
        modifier = collumnmodifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(paddingValues)
            .clip(CircleShape)
            .background(color = Color.Transparent, shape = CircleShape),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = imagemodifier
                .clip(CircleShape)
                .background(color = Color.Transparent, shape = CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemonDetail)
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
        Text(text = label,
            style = MaterialTheme.typography.labelLarge)
    }
}

