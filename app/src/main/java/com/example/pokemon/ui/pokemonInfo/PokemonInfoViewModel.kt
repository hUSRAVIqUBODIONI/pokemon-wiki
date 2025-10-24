package com.example.pokemon.ui.pokemonInfo

import androidx.lifecycle.ViewModel
import com.example.pokemon.data.remote.response.Pokemon
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokimonInfo(name: String) : Resource<Pokemon>{
        return repository.getPokemon(name)
    }

}