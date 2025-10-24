package com.example.pokemon.ui.pokemonListScreen



import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokemon.data.models.PokemonCard
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.utils.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class PokeminListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private var currPage = 0

    var pokemonList = mutableStateOf<List<PokemonCard>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    var isSearching = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokemonCard>()
    private var isSearchStarting = true




    init {
        loadPokemonPagination()
    }


    fun loadPokemonPagination(){
        viewModelScope.launch {
            val result = repository.getPokemonList(20,currPage*20)
            when (result){
                is Success -> {
                    if (pokemonList.value.size == 1302){
                        endReached.value = true
                    }
                    val pokemonListResult = result.date!!.results.mapIndexed { ind,result ->
                        val pokemonInd = ind + 1 + currPage*20
                        PokemonCard(name = result.name, id = pokemonInd, imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonInd}.png")
                    }
                    pokemonList.value += pokemonListResult
                    isLoading.value = false
                    currPage++
                }
                is Error -> {
                    loadError.value = result.message ?: "Неизвестная ошибка"
                    isLoading.value = false
                }
                else -> {
                    isLoading.value = true
                }
            }
        }
    }


    fun calcDominantColor(drawable: Drawable, onFinish: (Color)->Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate {palette ->
            palette?.dominantSwatch?.rgb?.let {colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    fun onSearch(searchText: String) {
        var listForSearch = if (isSearchStarting){
            pokemonList.value
        }
        else{
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (searchText.isEmpty()){
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            var result = listForSearch.filter {
                it.name.startsWith(searchText.trim(), ignoreCase = true)
            }
            if (isSearchStarting)
            {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = result
            isSearching.value = true
        }
    }

}

