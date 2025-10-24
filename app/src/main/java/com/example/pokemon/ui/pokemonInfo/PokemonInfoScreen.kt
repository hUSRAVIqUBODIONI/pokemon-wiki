package com.example.pokemon.ui.pokemonInfo

import android.util.Log
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.pokemon.R
import com.example.pokemon.data.remote.response.Pokemon
import com.example.pokemon.data.remote.response.Stat
import com.example.pokemon.data.remote.response.Type
import com.example.pokemon.utils.Resource
import com.example.pokemon.utils.getTypeColor
import timber.log.Timber

@Composable
fun PokemonInfoScreen(
    navController: NavController = rememberNavController(),
    pokemonName: String,
    pokemonDominantColor: Color,
    viewModel: PokemonInfoViewModel
) {
    var pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokimonInfo(name = pokemonName)
    }

    val pages = listOf<@Composable () -> Unit>(
        {PokemonDetailInfo(
            pokemonInfo.value.date!!,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .offset(y = 100.dp)
        )},
        {Test()}
    )
    val pagerState = rememberPagerState {pages.size}

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == 1) {
            pagerState.animateScrollToPage(0)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pokemonDominantColor)

    ) {
        topBarPokemonInfo(
            navController,
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter),
            pokemonDominantColor
        )

        WrapperPokemonInfo(
            pokemonInfo.value,
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = 20.dp + 130.dp / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            errorModifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .align(Alignment.Center)
                .padding(
                    top = 20.dp + 130.dp / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            if (pokemonInfo.value is Resource.Success) {

                Box(
                    modifier = Modifier
                        .offset(y = 100.dp + 100.dp)
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.6f)
                        .clip(shape = RoundedCornerShape(50.dp))
                        .background(Color.White)
                ) {
                    HorizontalPager(state = pagerState) { page ->
                        pages[page]()

                    }



                }

                AsyncImage(
                    model = pokemonInfo.value.date?.sprites?.front_default,
                    contentDescription = pokemonName,
                    modifier = Modifier
                        .size(200.dp)
                        .offset(y = 100.dp),
                )
            }
        }
    }

}




@Composable
fun Test(){
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp).offset(y=100.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text("Ahahah")
    }

}


@Composable
fun PokemonDetailInfo(pokemon: Pokemon, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Timber.tag("Test").d(pokemon.forms.toString())

        Text(
            text = "#${pokemon.id} - ${pokemon.name.replaceFirstChar { it.uppercaseChar() }}",
            fontWeight = FontWeight.Bold
        )
        PokemonTypes(pokemon.types)
        PokemonMainData(
            pokemon.height,
            pokemon.weight
        )
        Spacer(modifier= Modifier.height(10.dp))
        PokemonStats(pokemon)

    }
}


@Composable
fun PokemonStats(pokemon: Pokemon) {
    Text(text = "Stats:", modifier = Modifier
        .fillMaxWidth(),
        textAlign = TextAlign.Start)
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {


        for (stat in pokemon.stats) {

            DetailStat(stat)

        }
    }
}


@Composable
fun DetailStat(stat: Stat){
    var progress by remember { mutableStateOf(0f) }

    val animetedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(Unit) {
        progress = stat.base_stat.toFloat()/100f
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(fraction = animetedProgress).background(Color.Red)
        ) {
            Text(text = stat.stat.name.first().toString())
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    value: Float,
    unit: String,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${value}$unit"
        )
    }

}


@Composable
fun PokemonMainData(
    pokemonHeight: Int,
    pokemonWeight: Int,
    modifier: Modifier = Modifier
) {

    Row {
        PokemonDetailDataSection(
            value = (pokemonHeight * 100f) / 1000f,
            unit = "m",
            painter = painterResource(R.drawable.icons8),
            modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, 50.dp)
                .background(Color.Gray)
        )
        PokemonDetailDataSection(
            value = (pokemonWeight * 100f) / 1000f,
            unit = "kg",
            painter = painterResource(R.drawable.icons8_____96),
            modifier.weight(1f)
        )
    }

}


@Composable
fun PokemonTypes(types: List<Type>) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .horizontalScroll(rememberScrollState()),
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(130.dp)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(getTypeColor(type.type.name))

            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = type.type.name,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}


@Composable
fun topBarPokemonInfo(
    navController: NavController,
    modifier: Modifier = Modifier,
    pokemonDominantColor: Color
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        pokemonDominantColor
                    )
                )
            )
            .systemBarsPadding()
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(top = 50.dp, start = 5.dp)
                .size(36.dp)
                .clickable {
                    navController.popBackStack()
                }

        )
    }
}


@Composable
fun WrapperPokemonInfo(
    pokemonInfo: Resource<Pokemon>,
    loadingModifier: Modifier = Modifier,
    errorModifier: Modifier = Modifier
) {
    when (pokemonInfo) {
        is Resource.Error<Pokemon> -> {
            Box(
                modifier = errorModifier,
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = pokemonInfo.message!!,
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 30.sp,
                        ),
                    )
                    Row() {
                        IconButton(
                            onClick = {},
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null
                            )
                        }
                        IconButton(
                            onClick = {

                            },

                            ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        is Resource.Loading<Pokemon> -> {
            CircularProgressIndicator(
                modifier = loadingModifier

            )
        }

        is Resource.Success<*> -> {

        }
    }

}