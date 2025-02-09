package com.dominio.pokedex.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.dominio.pokedex.R
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoUIState
import com.dominio.pokedex.main.ui.theme.CustomLightColors
import java.util.Locale

@Composable
fun AppBarPokemonTitle() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Pokémons",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = CustomLightColors.secondary,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun AppBarPokemonDetail(
    navController: NavController,
    pokemonDetail: PokemonInfoUIState
) {
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
                painter = painterResource(id = R.drawable.leftarrow),
                contentDescription = "back")
        }
        Row(
            modifier = Modifier.weight(0.9F),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "${pokemonDetail.pokemon?.id} - ${pokemonDetail.pokemon?.name.toString().replaceFirstChar { if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()) else it.toString() }}    ",
                style = MaterialTheme.typography.headlineLarge,
                color = CustomLightColors.secondary
            )
        }
    }
}
