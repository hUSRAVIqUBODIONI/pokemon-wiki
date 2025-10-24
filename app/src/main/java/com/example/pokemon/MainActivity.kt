package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon.ui.pokemonInfo.PokemonInfoScreen
import com.example.pokemon.ui.pokemonInfo.PokemonInfoViewModel
import com.example.pokemon.ui.pokemonListScreen.PokemonListScreen
import com.example.pokemon.ui.theme.PokemonTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokemonTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "pokemon_list_screen") {
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController = navController)
                    }

                    composable(
                        "pokemon_detail_list/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominantColor") { type = NavType.IntType },
                            navArgument("pokemonName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->

                        val dominantColor = remember {
                            val color = backStackEntry.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }

                        val pokemonName = remember {
                            backStackEntry.arguments?.getString("pokemonName")
                        }

                        val viewModel: PokemonInfoViewModel = hiltViewModel(backStackEntry)

                        PokemonInfoScreen(
                            navController = navController,
                            pokemonName = pokemonName!!,
                            pokemonDominantColor = dominantColor,
                            viewModel = viewModel)
                    }

                }
            }
        }
    }
}

