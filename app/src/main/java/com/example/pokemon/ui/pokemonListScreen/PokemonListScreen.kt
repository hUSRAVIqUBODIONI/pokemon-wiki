package com.example.pokemon.ui.pokemonListScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokemon.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.pokemon.data.models.PokemonCard
import timber.log.Timber

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokeminListViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primaryContainer
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
                .padding(bottom = 10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.pokemon_23),
                contentDescription = "App Logo",

            )
            Spacer(modifier.height(10.dp))

            Scaffold(
                containerColor = MaterialTheme.colorScheme.primaryContainer ,
                topBar = {SearchBar {
                    viewModel.onSearch(it)
                }}
            ) { innerPadding ->
                PokemonList(viewModel,innerPadding,navController)
            }
            
        }

    }
}

@Composable
fun PokemonList(
    viewModel: PokeminListViewModel,
    innerPaddingValues: PaddingValues,
    navController: NavController,
    modifier: Modifier = Modifier,
){
    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val isSearching by remember { viewModel.isSearching }
    LazyVerticalGrid(
        contentPadding = innerPaddingValues,
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize()
    ) {

        val itemCount = pokemonList.size


        itemsIndexed(pokemonList) { index,pokemon ->
            if (index >= itemCount - 1 && !endReached && !isSearching){
                viewModel.loadPokemonPagination()
            }
            PokemonCard(pokemon,navController,viewModel)
        }

    }
    Spacer(modifier.height(5.dp))
}


@Composable
fun PokemonCard(pokemonCard: PokemonCard,navController: NavController,
                viewModel: PokeminListViewModel)
{
    val defaultDominantColor = MaterialTheme.colorScheme.primaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    var isLoading = remember {viewModel.isLoading}

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .shadow(5.dp,RoundedCornerShape(10.dp))
            .clickable(onClick = {
                navController.navigate("pokemon_detail_list/${dominantColor.toArgb()}" +
                        "/${pokemonCard.name}")
            })
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor,
                    )
                )
            )
    ){
        Column{
            AsyncImage(
                model = pokemonCard.imageUrl,
                contentDescription = pokemonCard.name,
                onSuccess = {
                    viewModel.calcDominantColor(it.result.drawable) {
                        dominantColor = it
                    }
                    isLoading.value = false
                },
                onLoading = {
                    isLoading.value = true
                },
                modifier = Modifier.size(130.dp).align(Alignment.CenterHorizontally)
            )
            Text(
                text = pokemonCard.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }

    }
}


@Composable
fun SearchBar(modifier: Modifier = Modifier,
              onSearch:(String) -> Unit){
    var searchText by remember { mutableStateOf("") }
        TextField(
            value = searchText,
            onValueChange = {
                onSearch(it)
                searchText = it
            },
            placeholder = { Text("Search") },
            leadingIcon = { Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.pokemon),
                contentDescription = ""
            ) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent 
            ),
            modifier = modifier
                .fillMaxWidth()
                .shadow(elevation = 5.dp, shape = CircleShape)
                .background(color = Color.White, shape = CircleShape)

        )

}

