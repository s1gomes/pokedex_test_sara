package com.dominio.pokedex.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dominio.pokedex.R
import com.dominio.pokedex.main.composables.MainScreen
import com.dominio.pokedex.main.composables.PokemonDetailScreen
import com.dominio.pokedex.main.datalayer.pokemon.PokemonsViewModel
import com.dominio.pokedex.main.datalayer.pokemonInfo.PokemonInfoViewModel
import com.dominio.pokedex.main.navigation.Screen
import com.dominio.pokedex.main.ui.theme.PokeAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeAppComposeTheme  {
                PokemonsApplication()
            }
        }
    }
}


@Composable
fun PokemonsApplication(
    pokemonsViewModel: PokemonsViewModel = hiltViewModel(),
    pokemonInfoViewModel: PokemonInfoViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MAIN_SCREEN.name) {
        composable(Screen.MAIN_SCREEN.name) {
            MainScreen(
                navController,
                pokemonsViewModel
            )
        }
        composable(
            "pokemon_detail/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getString("pokemonId") ?: ""
            PokemonDetailScreen(pokemonId, navController, pokemonInfoViewModel)
        }
    }
}

